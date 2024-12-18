package com.gen.fitness.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gen.fitness.model.Produtos;
import com.gen.fitness.repository.ProdutosRepository;

import jakarta.validation.Valid;

@RequestMapping("/produtos")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {
	
	@Autowired
	private ProdutosRepository produtosRepository;

	// Lista os produtos
	@GetMapping("/all")
	public ResponseEntity<List<Produtos>> getAll() {
		return ResponseEntity.ok(produtosRepository.findAll());
	}
	
	 // Busca produtos pelo plano 
    @GetMapping("/plano/{plano}")
    public ResponseEntity<List<Produtos>> getByPlano(@PathVariable String plano) {
        return ResponseEntity.ok(produtosRepository.findAllByPlanoContainingIgnoreCase(plano));
    }
	
    // Cria um novo produto
	@PostMapping("/cadastrar")
	public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produtos){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtosRepository.save(produtos));
	}
	
	//Atualiza um produto
	@PutMapping("/atualizar")
	public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produtos){
		return produtosRepository.findById(produtos.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(produtosRepository.save(produtos)))
						.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	//Deleta um produto
	@DeleteMapping("/deletar/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Produtos> produto = produtosRepository.findById(id);

        if (produto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        produtosRepository.deleteById(id);
    }
}
