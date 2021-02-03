package com.example.demo.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);

}
