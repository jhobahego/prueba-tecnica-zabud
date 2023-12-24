package com.pruebatecnica.zabud.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
import com.pruebatecnica.zabud.repositories.ProductoRepository;
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
        List<Item> items = convertToItems(facturaRequest.items());
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

        // Guardar los items en la base de datos
        itemRepository.saveAll(items);
    }

    @Override
    public Factura calcularFactura(FacturaRequest facturaRequest) {
        // Se convierte a una lista de items
        List<Item> items = convertToItems(facturaRequest.items());

        // Se calcula el total de la factura
        double total = calcularTotal(facturaRequest.items());

        return Factura.builder()
                .valorTotal(total)
                .cliente(facturaRequest.cliente())
                .items(items)
                .build();
    }

    private List<Item> convertToItems(List<ItemRequest> itemRequests) {
        return itemRequests.stream()
                .map(itemRequest -> {
                    // Se consulta el producto en la base de datos
                    Producto producto = productoRepository.findByCodigo(itemRequest.producto().getCodigo())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado, por favor verifique el codigo"));

                    // Se valida que el producto que se va a registrar en la factura sea el mismo que se encuentra en la base de datos
                    if (!Objects.equals(producto.getNombre(), itemRequest.producto().getNombre()) ||
                            producto.getValor() != itemRequest.producto().getValor())
                        throw new RuntimeException("Producto "+ producto.getNombre() +" invalido, verifique los datos");

                    return Item.builder()
                            .cantidad(itemRequest.cantidad())
                            .producto(producto)
                            .valorTotal(itemRequest.cantidad() * producto.getValor())
                            .build();
                })
                .toList();
    }

    private final Function<Factura, FacturaResponse> buildFacturaResponse = factura -> FacturaResponse.builder()
            .cliente(factura.getCliente())
            .items(convertirItemsAItemsResponse(factura.getItems()))
            .valorTotal(factura.getValorTotal())
            .fecha(LocalDateTime.now().toString())
            .build();
    private final ProductoRepository productoRepository;

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
