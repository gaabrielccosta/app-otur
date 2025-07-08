package com.otur.otur.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoRequestVO {

    private Long userId;
    private int estrelas;
    private String texto;
}
