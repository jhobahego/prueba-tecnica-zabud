package com.pruebatecnica.zabud.models.entities.dto;

import lombok.Builder;

@Builder
public record ProductoResponse(
        Long id,
        String codigo,
        String nombre,
        double valor) {
}
