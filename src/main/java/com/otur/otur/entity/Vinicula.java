package com.otur.otur.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

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

    @OneToMany(mappedBy = "vinicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos = new ArrayList<>();
}

