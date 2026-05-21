package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.DetalleRecetaDTO;
import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.service.DetalleRecetaServicio;
import Grupo4.ProyectoDesarrollo.service.MedicamentoServicio;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalle-receta")
@RequiredArgsConstructor
public class DetalleRecetaController{

    private final DetalleRecetaServicio servicio;
    private final RecetaServicio recetaServicio;
    private final MedicamentoServicio medicamentoServicio;

    @GetMapping
    public ResponseEntity<List<DetalleRecetaDTO>> listar() {
        List<DetalleRecetaDTO> lista = servicio.findAll().stream()
                .map(DetalleRecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleRecetaDTO> obtener(@PathVariable Long id) {
        DetalleReceta obte = servicio.findById(id);
        if (obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DetalleRecetaDTO.fromEntity(obte));
    }

    @PostMapping
    public ResponseEntity<DetalleRecetaDTO> crear(@RequestBody DetalleRecetaDTO dto) {
        Receta receta = dto.getRecetaId() != null ? recetaServicio.findById(dto.getRecetaId()) : null;
        Medicamento medicamento = dto.getMedicamentoId() != null ? medicamentoServicio.findById(dto.getMedicamentoId()) : null;

        DetalleReceta dr = dto.toEntity(receta, medicamento);
        DetalleReceta guardado = servicio.save(dr);
        return ResponseEntity.ok(DetalleRecetaDTO.fromEntity(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleRecetaDTO> actualizar(@PathVariable Long id, @RequestBody DetalleRecetaDTO dto) {
        dto.setId(id);
        Receta receta = dto.getRecetaId() != null ? recetaServicio.findById(dto.getRecetaId()) : null;
        Medicamento medicamento = dto.getMedicamentoId() != null ? medicamentoServicio.findById(dto.getMedicamentoId()) : null;

        DetalleReceta dr = dto.toEntity(receta, medicamento);
        DetalleReceta actualizado = servicio.update(id, dr);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DetalleRecetaDTO.fromEntity(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Detalle eliminado");
    }
}
