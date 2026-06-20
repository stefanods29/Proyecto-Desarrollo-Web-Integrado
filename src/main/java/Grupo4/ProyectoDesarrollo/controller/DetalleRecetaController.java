package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.DetalleRecetaDTO;
import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.repository.DetalleRecetaRepository;
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
    private final DetalleRecetaRepository detalleRecetaRepository;

    @GetMapping
    public ResponseEntity<List<DetalleRecetaDTO>> listar() {
        List<DetalleRecetaDTO> lista = servicio.findAll().stream()
                .map(DetalleRecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleRecetaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(DetalleRecetaDTO.fromEntity(servicio.findById(id)));
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
        return ResponseEntity.ok(DetalleRecetaDTO.fromEntity(servicio.update(id, dr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Detalle eliminado");
    }

    @GetMapping("/receta/{recetaId}")
    public ResponseEntity<List<DetalleRecetaDTO>> obtenerPorReceta(@PathVariable Long recetaId) {
        List<DetalleRecetaDTO> lista = detalleRecetaRepository.findByRecetaId(recetaId).stream()
                .map(DetalleRecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/medicamento/{medicamentoId}")
    public ResponseEntity<List<DetalleRecetaDTO>> obtenerPorMedicamento(@PathVariable Long medicamentoId) {
        List<DetalleRecetaDTO> lista = detalleRecetaRepository.findByMedicamentoId(medicamentoId).stream()
                .map(DetalleRecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<DetalleRecetaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        List<DetalleRecetaDTO> lista = detalleRecetaRepository.buscarDetallesPorPacienteId(pacienteId).stream()
                .map(DetalleRecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/paciente/{pacienteId}/medicamentos-activos")
    public ResponseEntity<List<DetalleRecetaDTO>> obtenerDetallesMedicamentosActivosPorPaciente(@PathVariable Long pacienteId) {
        List<DetalleRecetaDTO> lista = detalleRecetaRepository.buscarDetallesMedicamentoActivoPorPacienteId(pacienteId).stream()
                .map(DetalleRecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
