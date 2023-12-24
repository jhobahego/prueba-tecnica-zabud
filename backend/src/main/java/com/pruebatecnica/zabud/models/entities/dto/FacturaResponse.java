package com.pruebatecnica.zabud.models.entities.dto;

import com.pruebatecnica.zabud.models.entities.Item;
import lombok.Builder;

import java.util.List;

@Builder
public record FacturaResponse(
        String cliente,
        List<ItemResponse> items,
        double valorTotal,
        String fecha) {
}
