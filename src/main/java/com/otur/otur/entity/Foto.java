package com.otur.otur.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "caminho", nullable = false)
    private String caminho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vinicula_id", nullable = false)
    @JsonBackReference
    private Vinicula vinicula;
}