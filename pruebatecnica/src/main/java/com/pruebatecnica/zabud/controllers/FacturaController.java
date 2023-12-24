package com.pruebatecnica.zabud.controllers;

import com.pruebatecnica.zabud.models.entities.dto.FacturaRequest;
import com.pruebatecnica.zabud.models.entities.dto.FacturaResponse;
import com.pruebatecnica.zabud.services.impl.FacturaService;
import com.pruebatecnica.zabud.services.impl.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final ProductoService productoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFacturaPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(facturaService.obtenerFacturaConProductos(id));
        } catch (Exception e) {
            // return not fount with e.getMessage()
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearFactura(@RequestBody FacturaRequest facturaRequest) {
        try {
            FacturaResponse facturaResponse = facturaService.guardarFactura(facturaRequest);

            return ResponseEntity.created(new URI("/api/facturas")).body(facturaResponse);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // retorna bad request con el mensaje que se manda desde el service
            return ResponseEntity.badRequest().build();
        }
    }
}
