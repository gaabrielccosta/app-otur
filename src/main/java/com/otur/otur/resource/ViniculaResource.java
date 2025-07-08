package com.otur.otur.resource;

import com.otur.otur.entity.Avaliacao;
import com.otur.otur.entity.Vinicula;
import com.otur.otur.service.ViniculaService;
import com.otur.otur.vo.AvaliacaoRequestVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/viniculas")
public class ViniculaResource {

    private final ViniculaService viniculaService;

    public ViniculaResource(ViniculaService viniculaService) {
        this.viniculaService = viniculaService;
    }

    @GetMapping
    public ResponseEntity<List<Vinicula>> listar() {
        return ResponseEntity.ok(viniculaService.listar());
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
            @RequestPart(value = "fotos", required = false) MultipartFile[] fotos
    ) {
        viniculaService.postar(nome, horarios, instagram, localizacao, fotos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/avaliacoes")
    public ResponseEntity<List<Avaliacao>> listarAvaliacoes(@PathVariable Long id) {
        return ResponseEntity.ok(viniculaService.listarAvaliacoes(id));
    }

    @PostMapping(
            path = "/{id}/avaliacoes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Avaliacao> adicionarAvaliacao(
            @PathVariable Long id,
            @RequestBody AvaliacaoRequestVO body
    ) {
        Avaliacao criada = viniculaService.adicionarAvaliacao(
                id,
                body.getUserId(),
                body.getEstrelas(),
                body.getTexto()
        );
        return ResponseEntity.ok(criada);
    }
}
