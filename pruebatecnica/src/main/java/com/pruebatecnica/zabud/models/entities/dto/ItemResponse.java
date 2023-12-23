package com.pruebatecnica.zabud.models.entities.dto;

import com.pruebatecnica.zabud.models.entities.Producto;
import lombok.Builder;

@Builder
public record ItemResponse(
        int cantidad,
        double valorTotal,
        Producto producto
) {
}
