package com.pruebatecnica.zabud.models.entities.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FacturaRequest(
        String cliente,
        List<ItemRequest> items
) {
}
