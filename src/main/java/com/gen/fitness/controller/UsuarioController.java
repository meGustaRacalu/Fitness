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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gen.fitness.model.Usuario;
import com.gen.fitness.repository.UsuarioRepository;
import com.gen.fitness.service.UsuarioService;

import jakarta.validation.Valid;

	@RestController
	@RequestMapping("/usuarios")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public class UsuarioController {

	    @Autowired
	    private UsuarioRepository usuarioRepository;

	    @Autowired
	    private UsuarioService usuarioService;

	    // Método para listar todos os usuários com IMC calculado
	    @GetMapping("/all")
	    public ResponseEntity<List<Usuario>> getAllUsuarios() {
	        List<Usuario> usuarios = usuarioRepository.findAll();

	        // Calcula o IMC para todos os usuários
	        for (Usuario usuario : usuarios) {
	            usuarioService.calcularIMCParaUsuario(usuario);
	        }

	        return ResponseEntity.ok(usuarios);
	    }


	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return usuarioRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
    

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@RequestBody @Valid Usuario usuario) {
	    if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }

	    Usuario novoUsuario = usuarioRepository.save(usuario); 
	    return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
	    return usuarioRepository.findById(usuario.getId()) 
	            .map(u -> {
	                
	                u.setNome(usuario.getNome());
	                u.setSenha(usuario.getSenha());
	                u.setFoto(usuario.getFoto());
	                u.setUsuario(usuario.getUsuario());
	                u.setPeso(usuario.getPeso());  // Atualizando o peso
	                u.setAltura(usuario.getAltura());  // Atualizando a altura
	                usuarioRepository.save(u); 
	                return ResponseEntity.ok(u);
	            })
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletar/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        usuarioRepository.deleteById(id);
    }

}