package com.otur.otur.service;

import com.otur.otur.entity.Avaliacao;
import com.otur.otur.entity.Foto;
import com.otur.otur.entity.User;
import com.otur.otur.entity.Vinicula;
import com.otur.otur.repository.AvaliacaoRepository;
import com.otur.otur.repository.UserRepository;
import com.otur.otur.repository.ViniculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ViniculaService {

    private final ViniculaRepository viniculaRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final UserRepository userRepository;

    // pasta onde as fotos serão salvas (pode ajustar ou externalizar via @Value)
    private static final String UPLOAD_DIR = "uploads";

    public ViniculaService(ViniculaRepository viniculaRepository, AvaliacaoRepository avaliacaoRepository, UserRepository userRepository) {
        this.viniculaRepository = viniculaRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.userRepository = userRepository;
    }

    public List<Vinicula> listar() {
        return viniculaRepository.findAll();
    }

    @Transactional
    public void postar(
            String nome,
            String horarios,
            String instagram,
            String localizacao,
            MultipartFile[] files
    ) {
        // Cria entidade principal
        Vinicula vin = new Vinicula();
        vin.setNome(nome);
        vin.setHorarios(horarios);
        vin.setInstagram(instagram);
        vin.setLocalizacao(localizacao);

        // Garante lista inicializada
        if (vin.getFotos() == null) {
            vin.setFotos(new java.util.ArrayList<>());
        }

        // Garante existência do diretório
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Processa cada arquivo
        if (files != null) {
            Arrays.stream(files)
                    .filter(f -> f != null && !f.isEmpty())
                    .forEach(file -> {
                        try {
                            // gera nome único
                            String original = file.getOriginalFilename();
                            String ext = "";
                            if (original != null && original.contains(".")) {
                                ext = original.substring(original.lastIndexOf("."));
                            }
                            String filename = UUID.randomUUID().toString() + ext;

                            // salva em disco
                            File destino = new File(uploadDir, filename);
                            file.transferTo(destino);

                            // monta e adiciona a entidade Foto
                            Foto foto = new Foto();
                            foto.setNome(original);
                            foto.setCaminho(UPLOAD_DIR + "/" + filename);
                            foto.setVinicula(vin);
                            vin.getFotos().add(foto);

                        } catch (IOException e) {
                            throw new RuntimeException(
                                    "Erro ao salvar imagem " + file.getOriginalFilename(), e
                            );
                        }
                    });
        }

        viniculaRepository.save(vin);
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
