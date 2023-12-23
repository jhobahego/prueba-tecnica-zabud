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

//    TODO: Me da un badrequest ya que no me acepta el id en el body del producto
//    TODO: Si no se lo paso me crea el producto nuevamente en la base en vez de traerme el que ya existe
    @PostMapping
    public ResponseEntity<?> crearFactura(@RequestBody FacturaRequest facturaRequest) {
        // Se obtienen el codigo de los productos que se van a registrar en la factura
        List<String> codigosDeProducto = facturaRequest.items().stream().map(itemRequest -> itemRequest.producto().getCodigo()).toList();

        // Se valida que los productos existan
        boolean productoInexistente = codigosDeProducto.stream().allMatch(productoId -> productoService.obtenerPorCodigo(productoId) == null);

        if (productoInexistente) return ResponseEntity.status(404).body("Producto no encontrado, por favor verifique el codigo");

        try {
            FacturaResponse facturaResponse = facturaService.guardarFactura(facturaRequest);

            return ResponseEntity.created(new URI("/api/facturas")).body(facturaResponse);

        } catch (Exception e) {
            e.printStackTrace();
            // retorna bad request con el mensaje que se manda desde el service
            return ResponseEntity.badRequest().build();
        }
    }
}
