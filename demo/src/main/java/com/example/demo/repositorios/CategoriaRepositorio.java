package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

}
