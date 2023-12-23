package com.pruebatecnica.zabud.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.pruebatecnica.zabud.models.entities.Factura;
import com.pruebatecnica.zabud.models.entities.Item;
import com.pruebatecnica.zabud.models.entities.Producto;
import com.pruebatecnica.zabud.models.entities.dto.FacturaRequest;
import com.pruebatecnica.zabud.models.entities.dto.FacturaResponse;
import com.pruebatecnica.zabud.models.entities.dto.ItemRequest;
import com.pruebatecnica.zabud.models.entities.dto.ItemResponse;
import com.pruebatecnica.zabud.repositories.FacturaRepository;
import com.pruebatecnica.zabud.repositories.ItemRepository;
import com.pruebatecnica.zabud.services.IFacturaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FacturaService implements IFacturaService {

    private final FacturaRepository facturaRepository;
    private final ItemRepository itemRepository;

    @Override
    public FacturaResponse obtenerFacturaConProductos(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId).orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        return buildFacturaResponse.apply(factura);
    }

    @Override
    public FacturaResponse guardarFactura(FacturaRequest facturaRequest) {
        Factura factura = calcularFactura(facturaRequest);

        facturaRepository.save(factura);

        // Guardar los items en la base de datos
        List<Item> items = fromItemRequestToItems.apply(facturaRequest.items());
        guardarItemsEnBD(factura.getId(), items);

        // Se convierte la factura a una facturaResponse para retornarla
        return buildFacturaResponse.apply(factura);
    }

    @Transactional
    public void guardarItemsEnBD(Long facturaId, List<Item> items) {
        Optional<Factura> factura = facturaRepository.findById(facturaId);
        if (factura.isEmpty()) throw new RuntimeException("Factura no encontrada con ID: " + facturaId);

        // Asociar el item con la factura
        items.forEach(item -> item.setFactura(factura.get()));

        // Guardar el item en la base de datos
        itemRepository.saveAll(items);
    }

    @Override
    public Factura calcularFactura(FacturaRequest facturaRequest) {
        Factura factura = Factura.builder()
                .valorTotal(calcularTotal(facturaRequest.items()))
                .cliente(facturaRequest.cliente())
                // Se convierte a una lista de items
                .items(fromItemRequestToItems.apply(facturaRequest.items()))
                .build();

        return factura;
    }

    private final Function<List<ItemRequest>, List<Item>> fromItemRequestToItems = items -> items.stream().map(item ->
                    Item.builder()
                            .cantidad(item.cantidad())
                            .producto(item.producto())
                            .valorTotal(item.cantidad() * item.producto().getValor())
                            .build())
            .toList();

    private final Function<Factura, FacturaResponse> buildFacturaResponse = factura -> FacturaResponse.builder()
            .cliente(factura.getCliente())
            .items(convertirItemsAItemsResponse(factura.getItems()))
            .valorTotal(factura.getValorTotal())
            .fecha(LocalDateTime.now().toString())
            .build();

    private List<ItemResponse> convertirItemsAItemsResponse(List<Item> items) {
        return items.stream().map(item -> ItemResponse.builder()
                .cantidad(item.getCantidad())
                .producto(item.getProducto())
                .valorTotal(item.getValorTotal())
                .build()).toList();
    }

    private double calcularTotal(List<ItemRequest> items) {
        double total = 0;
        for (ItemRequest item : items) {
            total += item.producto().getValor() * item.cantidad();
        }
        return total;
    }
}
