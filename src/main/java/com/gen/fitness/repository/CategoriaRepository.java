package com.gen.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.fitness.model.CategoriaModel;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

    List<CategoriaModel> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

}
