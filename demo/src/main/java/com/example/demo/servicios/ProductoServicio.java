package com.example.demo.servicios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.modelos.Producto;
import com.example.demo.repositorios.ProductoRepositorio;
import com.example.demo.servicios.base.BaseService;

@Service
public class ProductoServicio extends BaseService<Producto, Long, ProductoRepositorio> {
	
	public Page<Producto> findByNombre(String txt, Pageable pageable) {
		return this.repositorio.findByNombreContainsIgnoreCase(txt, pageable);
	}

	public Page<Producto> findByArgs(final Optional<String> nombre, final Optional<Float> precio, Pageable pageable) {

		Specification<Producto> specNombreProducto = (root, query, criteriaBuilder) -> {
			if (nombre.isPresent()) {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.get() + "%");
			} else {
				return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
			}
		};

		Specification<Producto> specPrecioMenorQue = (root, query, criteriaBuilder) -> {
			if (precio.isPresent()) {
				return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
			} else {
				return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
			}
		};
		
		Specification<Producto> specAmbas = specNombreProducto.and(specPrecioMenorQue);
		
		return this.repositorio.findAll(specAmbas, pageable);
	}
}
