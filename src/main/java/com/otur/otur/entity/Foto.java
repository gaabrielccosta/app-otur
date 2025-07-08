package com.otur.otur.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fotos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "conteudo", nullable = false)
    private String caminho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vinicula_id", nullable = false)
    private Vinicula vinicula;
}