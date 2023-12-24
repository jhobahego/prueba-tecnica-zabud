package com.pruebatecnica.zabud.controllers;

import com.pruebatecnica.zabud.models.entities.Producto;
import com.pruebatecnica.zabud.models.entities.dto.ProductoRequest;
import com.pruebatecnica.zabud.models.entities.dto.ProductoResponse;
import com.pruebatecnica.zabud.services.impl.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@AllArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoRequest productoRequest) throws URISyntaxException {
        ProductoResponse productoResponse = productoService.crearProducto(productoRequest);

        return ResponseEntity.created(new URI("/api/productos")).body(productoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequest productoRequest) {
        Producto productoActualizado = productoService.actualizarProducto(id, productoRequest);

        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}
