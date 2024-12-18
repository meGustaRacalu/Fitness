package com.gen.fitness.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gen.fitness.model.Usuario;
import com.gen.fitness.model.UsuarioLogin;
import com.gen.fitness.repository.UsuarioRepository;
import com.gen.fitness.security.JwtService;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
	
    public float calcularIMC(Usuario usuario) {
        if (usuario.getAltura() > 0) {
            return usuario.getPeso() / (usuario.getAltura() * usuario.getAltura()); 
        }
        return 0; 
    }

    public Usuario calcularIMCParaUsuario(Usuario usuario) {
        usuario.setImc(calcularIMC(usuario)); 
        return usuario;
    }
    
    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent() 
				|| (usuario.getId() != null && usuarioRepository.existsById(usuario.getId())))
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	}
    
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
        
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());
		
		Authentication authentication = authenticationManager.authenticate(credenciais);
        
		if (authentication.isAuthenticated()) {
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
			if (usuario.isPresent()) {
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
				usuarioLogin.get().setSenha("");
				
				return usuarioLogin;
			}
        } 
		return Optional.empty();
    }

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}
}
