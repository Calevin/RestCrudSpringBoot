package com.example.demo.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Pedido;
import com.example.demo.modelos.Usuario;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
	
	Page<Pedido> findByCliente(Usuario cliente, Pageable pageable);

}
