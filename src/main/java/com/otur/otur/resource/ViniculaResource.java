package com.otur.otur.resource;

import com.otur.otur.dto.FotoDTO;
import com.otur.otur.dto.ViniculaDTO;
import com.otur.otur.entity.Avaliacao;
import com.otur.otur.service.ViniculaService;
import com.otur.otur.vo.AvaliacaoRequestVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/viniculas")
public class ViniculaResource {

    private final ViniculaService viniculaService;

    public ViniculaResource(ViniculaService viniculaService) {
        this.viniculaService = viniculaService;
    }

    @GetMapping
    public List<ViniculaDTO> listar() {
        return viniculaService.listar().stream()
                .map(vin -> {
                    ViniculaDTO dto = new ViniculaDTO();
                    dto.setId(vin.getId());
                    dto.setNome(vin.getNome());
                    dto.setHorarios(vin.getHorarios());
                    dto.setInstagram(vin.getInstagram());
                    dto.setLocalizacao(vin.getLocalizacao());
                    dto.setFotos(
                            vin.getFotos().stream()
                                    .map(foto -> {
                                        FotoDTO f = new FotoDTO();
                                        f.setId(foto.getId());
                                        f.setNome(foto.getNome());
                                        f.setTipo(foto.getTipo());
                                        // extrai só o nome do arquivo do caminho completo
                                        String filename = Paths
                                                .get(foto.getCaminho())
                                                .getFileName()
                                                .toString();
                                        f.setCaminho(filename);
                                        return f;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/postar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> postar(
            @RequestParam Integer id,
            @RequestParam String nome,
            @RequestParam String horarios,
            @RequestParam String instagram,
            @RequestParam String localizacao,
            @RequestParam String novo,
            @RequestParam(value = "fotos", required = false) MultipartFile[] fotos
    ) {
        viniculaService.postar(id, nome, horarios, instagram, localizacao, novo, fotos);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deletar/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Integer id) {
        viniculaService.apagar(id);
        return ResponseEntity.noContent().build();
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
