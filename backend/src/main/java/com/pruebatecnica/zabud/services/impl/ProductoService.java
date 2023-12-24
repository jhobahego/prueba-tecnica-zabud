package com.pruebatecnica.zabud.services.impl;

import com.pruebatecnica.zabud.exceptions.BadRequest;
import com.pruebatecnica.zabud.exceptions.ResourceNotFound;
import com.pruebatecnica.zabud.models.entities.Producto;
import com.pruebatecnica.zabud.models.entities.dto.ProductoRequest;
import com.pruebatecnica.zabud.models.entities.dto.ProductoResponse;
import com.pruebatecnica.zabud.repositories.ProductoRepository;
import com.pruebatecnica.zabud.services.IProductService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductoService implements IProductService {

    private final ProductoRepository productoRepository;

    @Override
    public Producto obtenerPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFound("No se encontró el producto"));
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Producto con ID: " + id + " no encontrado"));
    }

    @Override
    public ProductoResponse crearProducto(ProductoRequest productoRequest) {
        if (productoRequest.nombre() == null || productoRequest.nombre().isEmpty()) {
            throw new BadRequest("El nombre del producto es requerido");
        }

        if (productoRequest.valor() <= 0) {
            throw new BadRequest("El valor del producto es requerido");
        }

        Producto producto = productoRepository.save(Producto.builder()
                .codigo(productoRequest.nombre() + UUID.randomUUID())
                .nombre(productoRequest.nombre())
                .valor(productoRequest.valor())
                .build());

        return convertirAProductoResponse(producto);
    }

    @Override
    public Producto actualizarProducto(Long id, ProductoRequest productoRequest) {
        String nombre = productoRequest.nombre();
        double valor = productoRequest.valor();

        if (nombre == null || nombre.isEmpty()) {
            throw new BadRequest("El nombre del producto es requerido");
        }

        if (valor <= 0) {
            throw new BadRequest("El valor del producto debe ser mayor que 0");
        }

        Producto producto = obtenerPorId(id);

        producto.setNombre(nombre);
        producto.setValor(valor);

        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        obtenerPorId(id);

        productoRepository.deleteById(id);
    }

    private ProductoResponse convertirAProductoResponse(Producto producto) {
         return ProductoResponse.builder()
                .id(producto.getId())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .valor(producto.getValor())
                .build();
    }
}
