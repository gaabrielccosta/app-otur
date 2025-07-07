package com.otur.otur.service;

import com.otur.otur.entity.Foto;
import com.otur.otur.entity.Vinicula;
import com.otur.otur.repository.ViniculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ViniculaService {

    private final ViniculaRepository viniculaRepository;

    public ViniculaService(ViniculaRepository viniculaRepository) {
        this.viniculaRepository = viniculaRepository;
    }

    @Transactional
    public void postar(
            String nome,
            String horarios,
            String instagram,
            String localizacao,
            MultipartFile[] files
    ) {
        Vinicula vin = new Vinicula();
        vin.setNome(nome);
        vin.setHorarios(horarios);
        vin.setInstagram(instagram);
        vin.setLocalizacao(localizacao);

        // Verifica se há arquivos antes de processar
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        Foto foto = new Foto();
                        foto.setNome(file.getOriginalFilename());
                        foto.setTipo(file.getContentType());
                        foto.setConteudo(bytes);
                        foto.setVinicula(vin);
                        vin.getFotos().add(foto);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao processar imagem: " + file.getOriginalFilename(), e);
                    }
                }
            }
        }

        viniculaRepository.save(vin);
    }
}
