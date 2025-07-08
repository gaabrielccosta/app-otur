package com.otur.otur.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int estrelas; // 1 a 5

    @Column(columnDefinition = "TEXT")
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vinicula_id")
    @JsonIgnoreProperties("fotos")
    private Vinicula vinicula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;    // << adiciona usuário

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Avaliacao() { }

    public Avaliacao(int estrelas, String texto, Vinicula vinicula, User user) {
        this.estrelas   = estrelas;
        this.texto      = texto;
        this.vinicula   = vinicula;
        this.user       = user;
        this.createdAt  = LocalDateTime.now();
    }
}
