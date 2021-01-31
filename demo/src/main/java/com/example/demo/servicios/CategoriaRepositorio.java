package com.example.demo.servicios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

}
