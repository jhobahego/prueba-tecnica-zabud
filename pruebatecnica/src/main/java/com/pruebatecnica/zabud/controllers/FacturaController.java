package com.pruebatecnica.zabud.controllers;

import com.pruebatecnica.zabud.models.entities.dto.FacturaRequest;
import com.pruebatecnica.zabud.models.entities.dto.FacturaResponse;
import com.pruebatecnica.zabud.services.impl.FacturaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@AllArgsConstructor
@RestController
@RequestMapping("api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFacturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(facturaService.obtenerFacturaConProductos(id));
    }

    @PostMapping
    public ResponseEntity<?> crearFactura(@RequestBody FacturaRequest facturaRequest) throws URISyntaxException {
        FacturaResponse facturaResponse = facturaService.guardarFactura(facturaRequest);

        return ResponseEntity.created(new URI("/api/facturas")).body(facturaResponse);
    }
}
