package com.otur.otur.repository;

import com.otur.otur.entity.Avaliacao;
import com.otur.otur.entity.Vinicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long> {

    List<Avaliacao> findByVinicula(Vinicula vinicula);
}
