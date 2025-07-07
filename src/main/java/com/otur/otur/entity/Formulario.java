package com.otur.otur.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "formularios")
@Getter
@Setter
public class Formulario {

    @Id
    private Long id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jsonResponse;
}
