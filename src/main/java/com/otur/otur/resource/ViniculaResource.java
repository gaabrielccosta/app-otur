package com.otur.otur.resource;

import com.otur.otur.service.ViniculaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/viniculas")
public class ViniculaResource {

    private final ViniculaService viniculaService;

    public ViniculaResource(ViniculaService viniculaService) {
        this.viniculaService = viniculaService;
    }

    @PostMapping(
            path = "/postar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> postar(
            @RequestParam("nome") String nome,
            @RequestParam("horarios") String horarios,
            @RequestParam("instagram") String instagram,
            @RequestParam("localizacao") String localizacao,
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos
    ) {
        if (fotos != null) {
            System.out.println(fotos.size());
            System.out.println();
        } else {
            System.out.println("NULO");
        }
        MultipartFile[] files = fotos != null ? fotos.toArray(new MultipartFile[0]) : new MultipartFile[0];
        viniculaService.postar(nome, horarios, instagram, localizacao, files);
        return ResponseEntity.ok().build();
    }
}
