package com.otur.otur.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ViniculaDTO {

    private Long id;
    private String nome;
    private String horarios;
    private String instagram;
    private String localizacao;
    private List<FotoDTO> fotos;
}
