package com.pruebatecnica.zabud.services;

import com.pruebatecnica.zabud.models.entities.Producto;
import com.pruebatecnica.zabud.models.entities.dto.ProductoRequest;
import com.pruebatecnica.zabud.models.entities.dto.ProductoResponse;

import java.util.Optional;

public interface IProductService {

    Producto obtenerPorCodigo(String codigo);

    ProductoResponse crearProducto(ProductoRequest productoRequest);

    Producto actualizarProducto(Long id, ProductoRequest productoRequest);

    void eliminarProducto(Long id);
}
