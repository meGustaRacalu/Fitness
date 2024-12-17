package com.gen.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gen.fitness.model.Produtos;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

	
}
