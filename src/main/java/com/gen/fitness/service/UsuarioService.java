package com.gen.fitness.service;

import org.springframework.stereotype.Service;

import com.gen.fitness.model.Usuario;

@Service
public class UsuarioService {

    // Método para calcular IMC de um usuário
    public float calcularIMC(Usuario usuario) {
        if (usuario.getAltura() > 0) {
            return usuario.getPeso() / (usuario.getAltura() * usuario.getAltura()); // Fórmula do IMC
        }
        return 0; // Retorna 0 se a altura for inválida
    }

    // Método para calcular e aplicar o IMC ao usuário
    public Usuario calcularIMCParaUsuario(Usuario usuario) {
        usuario.setImc(calcularIMC(usuario)); // Calcula e define o IMC no objeto Usuario
        return usuario;
    }
}
