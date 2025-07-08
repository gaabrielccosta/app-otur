package com.otur.otur.repository;

import com.otur.otur.entity.Vinicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViniculaRepository extends JpaRepository<Vinicula,Long> {

    Optional<Vinicula> findByNome(String nome);
}
