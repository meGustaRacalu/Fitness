package com.gen.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gen.fitness.model.Produtos;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

	List<Produtos> findAllByPlanoContainingIgnoreCase(@Param("plano") String plano);
	}

