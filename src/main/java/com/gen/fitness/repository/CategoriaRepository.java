package com.gen.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gen.fitness.model.CategoriaModel;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

    List<CategoriaModel> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

}
