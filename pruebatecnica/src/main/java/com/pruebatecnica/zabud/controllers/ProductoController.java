package com.pruebatecnica.zabud.controllers;

import com.pruebatecnica.zabud.models.entities.dto.ProductoRequest;
import com.pruebatecnica.zabud.models.entities.dto.ProductoResponse;
import com.pruebatecnica.zabud.services.impl.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@AllArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoRequest productoRequest) {
        ProductoResponse productoResponse = productoService.crearProducto(productoRequest);

        if (productoResponse != null) {
            return ResponseEntity.ok(productoResponse);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequest productoRequest) {
        try {
            return ResponseEntity.created(new URI("/api/productos/".concat(id.toString()))).body(productoService.actualizarProducto(id, productoRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
