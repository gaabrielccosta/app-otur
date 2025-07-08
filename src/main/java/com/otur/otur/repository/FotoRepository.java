package com.otur.otur.repository;

import com.otur.otur.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto,Long> {

    List<Foto> findAllByViniculaId(Long viniculaId);
}