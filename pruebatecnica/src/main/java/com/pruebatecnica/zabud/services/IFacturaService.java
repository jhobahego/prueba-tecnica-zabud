package com.pruebatecnica.zabud.services;

import com.pruebatecnica.zabud.models.entities.Factura;
import com.pruebatecnica.zabud.models.entities.dto.FacturaRequest;
import com.pruebatecnica.zabud.models.entities.dto.FacturaResponse;


public interface IFacturaService {

    FacturaResponse obtenerFacturaConProductos(Long facturaId);

    FacturaResponse guardarFactura(FacturaRequest facturaRequest);

    Factura calcularFactura(FacturaRequest facturaRequest);
}
