package com.example.demo.servicios;

import org.springframework.stereotype.Service;

import com.example.demo.modelos.Producto;
import com.example.demo.repositorios.ProductoRepositorio;
import com.example.demo.servicios.base.BaseService;

@Service
public class ProductoServicio extends BaseService<Producto, Long, ProductoRepositorio> {
}
