package com.otur.otur.resource;

import com.otur.otur.entity.Formulario;
import com.otur.otur.service.FormularioService;
import com.otur.otur.vo.FormularioPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/formularios")
public class FormularioResource {

    @Autowired
    private FormularioService formularioService;

    @PutMapping("/buscar/{userId}")
    public ResponseEntity<Formulario> buscar(@PathVariable Long userId) {
        return ResponseEntity.ok(formularioService.buscar(userId));
    }

    @PostMapping("/postar")
    public ResponseEntity<Void> postar(@RequestBody FormularioPostVO vo) {
        formularioService.postar(vo);
        return ResponseEntity.ok().build();
    }
}
