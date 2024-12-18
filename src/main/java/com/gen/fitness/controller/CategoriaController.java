package com.gen.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.gen.fitness.model.CategoriaModel;
import com.gen.fitness.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Lista as categorias
    @GetMapping("/all")
    public List<CategoriaModel> getAll() {
        return categoriaRepository.findAll();
    }

    // Busca categoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaModel> getById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Busca categoria por descrição
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<CategoriaModel>> getByDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(categoriaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
    }

    // Cria uma nova categoria
    @PostMapping("/cadastrar")
    public CategoriaModel create(@RequestBody CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }

    // Atualiza uma categoria
    @PutMapping("/atualizar")
    public ResponseEntity<CategoriaModel> update(@RequestBody CategoriaModel categoria) {
        return categoriaRepository.findById(categoria.getId())  // Usando o ID do corpo da requisição
                .map(existingCategoria -> {
                    existingCategoria.setDescricao(categoria.getDescricao());
                    categoriaRepository.save(existingCategoria);
                    return ResponseEntity.ok(existingCategoria);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deleta uma categoria
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoriaRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}