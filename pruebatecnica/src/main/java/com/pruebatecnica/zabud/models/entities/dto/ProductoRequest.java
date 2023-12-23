package com.pruebatecnica.zabud.models.entities.dto;

import lombok.Builder;

@Builder
public record ProductoRequest(
        String nombre,
        double valor) {
}
