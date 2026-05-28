package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.DetalleFacturaDTO;
import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.service.DetalleFacturaService;
import Grupo4.ProyectoDesarrollo.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalle-facturas")
@RequiredArgsConstructor
public class DetalleFacturaController {

    private final DetalleFacturaService servicio;
    private final FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<DetalleFacturaDTO>> listar() {
        List<DetalleFacturaDTO> lista = servicio.listar().stream()
                .map(DetalleFacturaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleFacturaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(DetalleFacturaDTO.fromEntity(servicio.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<DetalleFacturaDTO> crear(@RequestBody DetalleFacturaDTO dto) {
        Factura factura = dto.getFacturaId() != null ? facturaService.buscarPorId(dto.getFacturaId()) : null;
        DetalleFactura df = dto.toEntity(factura);
        DetalleFactura guardado = servicio.crear(df);
        return ResponseEntity.ok(DetalleFacturaDTO.fromEntity(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleFacturaDTO> actualizar(@PathVariable Long id, @RequestBody DetalleFacturaDTO dto) {
        dto.setId(id);
        Factura factura = dto.getFacturaId() != null ? facturaService.buscarPorId(dto.getFacturaId()) : null;
        DetalleFactura df = dto.toEntity(factura);
        DetalleFactura actualizado = servicio.crear(df); // Note: DetalleFacturaServiceImpl's crear acts as save/update as it uses save()
        return ResponseEntity.ok(DetalleFacturaDTO.fromEntity(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.ok("Detalle de factura eliminado");
    }

    @GetMapping("/factura/{facturaId}")
    public ResponseEntity<List<DetalleFacturaDTO>> obtenerPorFactura(@PathVariable Long facturaId) {
        List<DetalleFacturaDTO> lista = servicio.buscarPorFacturaId(facturaId).stream()
                .map(DetalleFacturaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DetalleFacturaDTO>> buscarPorDescripcion(@RequestParam String descripcion) {
        List<DetalleFacturaDTO> lista = servicio.buscarPorDescripcion(descripcion).stream()
                .map(DetalleFacturaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<DetalleFacturaDTO>> obtenerPorClinica(@PathVariable Long clinicaId) {
        List<DetalleFacturaDTO> lista = servicio.buscarPorClinicaId(clinicaId).stream()
                .map(DetalleFacturaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
