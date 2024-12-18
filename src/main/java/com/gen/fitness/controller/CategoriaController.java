package com.gen.fitness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gen.fitness.model.CategoriaModel;
import com.gen.fitness.repository.CategoriaRepository;

import java.util.List;

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
    public ResponseEntity<CategoriaModel> update(@PathVariable Long id, @RequestBody CategoriaModel categoria) {
        return categoriaRepository.findById(id)
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