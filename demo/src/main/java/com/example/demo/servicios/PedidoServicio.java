package com.example.demo.servicios;

import org.springframework.stereotype.Service;

import com.example.demo.modelos.Pedido;
import com.example.demo.repositorios.PedidoRepositorio;
import com.example.demo.servicios.base.BaseService;

@Service 
public class PedidoServicio extends BaseService<Pedido, Long, PedidoRepositorio> {

}
