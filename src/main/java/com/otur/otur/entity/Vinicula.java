package com.otur.otur.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "viniculas")
@Getter
@Setter
public class Vinicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String horarios;

    @Column(nullable = false)
    private String instagram;

    @Column(nullable = false)
    private String localizacao;

    @OneToMany(mappedBy="vinicula", cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Foto> fotos;
}

