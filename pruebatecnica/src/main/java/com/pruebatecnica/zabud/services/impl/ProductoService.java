package com.pruebatecnica.zabud.services.impl;

import com.pruebatecnica.zabud.models.entities.Producto;
import com.pruebatecnica.zabud.models.entities.dto.ProductoRequest;
import com.pruebatecnica.zabud.models.entities.dto.ProductoResponse;
import com.pruebatecnica.zabud.repositories.ProductoRepository;
import com.pruebatecnica.zabud.services.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ProductoService implements IProductService {

    private final ProductoRepository productoRepository;

    @Override
    public Producto obtenerPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo).orElseThrow(() -> new RuntimeException("No se encontró el producto"));
    }

    @Override
    public ProductoResponse crearProducto(ProductoRequest productoRequest) {
        if (productoRequest.nombre() == null || productoRequest.nombre().isEmpty()) {
            throw new RuntimeException("El nombre del producto es requerido");
        }

        if (productoRequest.valor() <= 0) {
            throw new RuntimeException("El valor del producto es requerido");
        }

        Producto producto = productoRepository.save(Producto.builder()
                .codigo(productoRequest.nombre() + UUID.randomUUID())
                .nombre(productoRequest.nombre())
                .valor(productoRequest.valor())
                .build());

        return convertirProductoAProductoResponse(productoRequest).apply(producto);
    }

    @Override
    public ProductoResponse actualizarProducto(Long id, ProductoRequest productoRequest) {
        Optional<ProductoResponse> productoResponse = productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoRequest.nombre());
                    producto.setValor(productoRequest.valor());

                    return convertirProductoAProductoResponse(productoRequest).apply(producto);
                });

        return productoResponse.orElseThrow(() -> new RuntimeException("No se encontró el producto"));
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el producto"));

        productoRepository.deleteById(id);
    }

    private static Function<Producto, ProductoResponse> convertirProductoAProductoResponse(ProductoRequest productoRequest) {
        return producto -> ProductoResponse.builder()
                .id(producto.getId())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .valor(producto.getValor())
                .build();
    }
}
