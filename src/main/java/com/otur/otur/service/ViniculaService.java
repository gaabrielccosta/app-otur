package com.otur.otur.service;

import com.otur.otur.entity.Avaliacao;
import com.otur.otur.entity.Foto;
import com.otur.otur.entity.User;
import com.otur.otur.entity.Vinicula;
import com.otur.otur.repository.AvaliacaoRepository;
import com.otur.otur.repository.FotoRepository;
import com.otur.otur.repository.UserRepository;
import com.otur.otur.repository.ViniculaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ViniculaService {

    private final ViniculaRepository viniculaRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final UserRepository userRepository;
    private final FotoRepository fotoRepository;

    @Value("${upload.dir}")
    private String uploadDirPath;

    public ViniculaService(ViniculaRepository viniculaRepository,
                           AvaliacaoRepository avaliacaoRepository,
                           UserRepository userRepository,
                           FotoRepository fotoRepository) {
        this.viniculaRepository = viniculaRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.userRepository = userRepository;
        this.fotoRepository = fotoRepository;
    }

    public List<Vinicula> listar() {
        return viniculaRepository.findAll();
    }

    @Transactional
    public void postar(
            Integer id,
            String nome,
            String horarios,
            String instagram,
            String localizacao,
            String novo,
            MultipartFile[] files
    ) {
        // 1) Cria entidade Vinicula
        Vinicula vin = null;
        if (novo != null && novo.equals("false")) {
            vin = viniculaRepository.findById(id.longValue()).orElse(null);
        }
        if (vin == null) {
            vin = new Vinicula();
        } else {
            apagarFotosVinicula(id);
        }

        vin.setNome(nome);
        vin.setHorarios(horarios);
        vin.setInstagram(instagram);
        vin.setLocalizacao(localizacao);

        // 2) Cria lista de fotos
        List<Foto> listaFotos = new ArrayList<>();

        // 3) Garante que o diretório existe (throws em caso de falha)
        Path uploadPath = Paths.get(uploadDirPath);
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar pasta de upload", e);
        }

        // 4) Processa cada arquivo
        if (files != null) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                // pega nome original e extensão
                String original = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                String ext = "";
                int idx = original.lastIndexOf('.');
                if (idx > 0) {
                    ext = original.substring(idx);
                }

                // gera nome único
                String filename = UUID.randomUUID() + ext;
                Path destino = uploadPath.resolve(filename);

                // copia o conteúdo do MultipartFile para o destino
                try (InputStream in = file.getInputStream()) {
                    Files.copy(in, destino, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao salvar imagem " + original, e);
                }

                // monta entidade Foto
                Foto foto = new Foto();
                foto.setNome(original);
                foto.setCaminho(destino.toString().split("/")[destino.toString().split("/").length - 1]);
                foto.setVinicula(vin);
                foto.setTipo(file.getContentType());
                listaFotos.add(foto);
            }
        }

        vin.setFotos(listaFotos);
        viniculaRepository.save(vin);
    }

    @Transactional
    public void apagar(Integer id) {
        Vinicula vinicula = viniculaRepository.findById(id.longValue()).orElse(null);
        if (vinicula == null) return;

        viniculaRepository.deleteById(id.longValue());
    }

    @Transactional
    public void apagarFotosVinicula(Integer id) {
        List<Foto> fotos = fotoRepository.findAllByViniculaId(id.longValue());
        fotoRepository.deleteAll(fotos);
    }

    public List<Avaliacao> listarAvaliacoes(Long viniculaId) {
        Vinicula vin = viniculaRepository.findById(viniculaId)
                .orElseThrow(() -> new RuntimeException("Vinícula não encontrada: " + viniculaId));
        return avaliacaoRepository.findByVinicula(vin);
    }

    @Transactional
    public Avaliacao adicionarAvaliacao(Long viniculaId, Long userId, int estrelas, String texto) {
        Vinicula vin = viniculaRepository.findById(viniculaId)
                .orElseThrow(() -> new RuntimeException("Vinícola não encontrada: " + viniculaId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));

        Avaliacao aval = new Avaliacao(estrelas, texto, vin, user);
        return avaliacaoRepository.save(aval);
    }
}
