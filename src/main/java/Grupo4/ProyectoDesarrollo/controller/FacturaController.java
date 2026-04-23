package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {
    private final FacturaService service;

    @PostMapping
    public Factura crear(@RequestBody Factura factura) {
        return service.crear(factura);
    }

    @GetMapping
    public List<Factura> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Factura buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Factura actualizar(@PathVariable Long id, @RequestBody Factura factura) {
        factura.setIdFactura(id);
        return service.crear(factura);
    }
}
