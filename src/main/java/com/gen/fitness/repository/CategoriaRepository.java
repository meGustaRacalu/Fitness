package com.gen.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gen.fitness.model.CategoriaModel;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
}
