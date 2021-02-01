package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

}
