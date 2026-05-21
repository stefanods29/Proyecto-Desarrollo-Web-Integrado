package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.MedicamentoDTO;
import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.service.MedicamentoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final MedicamentoServicio servicio;

    @GetMapping
    public ResponseEntity<List<MedicamentoDTO>> listar() {
        List<MedicamentoDTO> lista = servicio.findAll().stream()
                .map(MedicamentoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> obtener(@PathVariable Long id) {
        Medicamento obte = servicio.findById(id);
        if (obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(MedicamentoDTO.fromEntity(obte));
    }

    @PostMapping
    public ResponseEntity<MedicamentoDTO> crear(@RequestBody MedicamentoDTO dto) {
        Medicamento medicamento = dto.toEntity();
        Medicamento guardado = servicio.save(medicamento);
        return ResponseEntity.ok(MedicamentoDTO.fromEntity(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> actualizar(@PathVariable Long id, @RequestBody MedicamentoDTO dto) {
        dto.setId(id);
        Medicamento medicamento = dto.toEntity();
        Medicamento actualizado = servicio.update(id, medicamento);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(MedicamentoDTO.fromEntity(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Medicamento eliminado");
    }
}
