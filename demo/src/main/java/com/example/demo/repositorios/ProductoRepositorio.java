package com.example.demo.repositorios;

import com.example.demo.modelos.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

	Page<Producto> findByNombreContainsIgnoreCase(String txt, Pageable pageable);

}
