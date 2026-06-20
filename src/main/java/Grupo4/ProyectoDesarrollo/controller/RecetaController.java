package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.RecetaDTO;
import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.repository.RecetaRepository;
import Grupo4.ProyectoDesarrollo.service.ConsultaMedicaServicio;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaServicio servicio;
    private final ConsultaMedicaServicio consultaMedicaServicio;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final RecetaRepository recetaRepository;

    @GetMapping
    public ResponseEntity<List<RecetaDTO>> listar() {
        List<RecetaDTO> lista = servicio.findAll().stream()
                .map(RecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaDTO> obtener(@PathVariable Long id) {
        Receta obte = servicio.findById(id);
        if (obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(RecetaDTO.fromEntity(obte));
    }

    @PostMapping
    public ResponseEntity<RecetaDTO> crear(@RequestBody RecetaDTO dto) {
        ConsultaMedica cm = dto.getConsultaMedicaId() != null ? consultaMedicaServicio.findById(dto.getConsultaMedicaId()) : null;
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;

        Receta receta = dto.toEntity(cm, medico, paciente);
        Receta guardada = servicio.save(receta);
        return ResponseEntity.ok(RecetaDTO.fromEntity(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDTO> actualizar(@PathVariable Long id, @RequestBody RecetaDTO dto) {
        dto.setId(id);
        ConsultaMedica cm = dto.getConsultaMedicaId() != null ? consultaMedicaServicio.findById(dto.getConsultaMedicaId()) : null;
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;

        Receta receta = dto.toEntity(cm, medico, paciente);
        Receta actualizado = servicio.update(id, receta);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(RecetaDTO.fromEntity(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Receta eliminada");
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<RecetaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        List<RecetaDTO> lista = recetaRepository.findByPacienteId(pacienteId).stream()
                .map(RecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/consulta/{consultaMedicaId}")
    public ResponseEntity<List<RecetaDTO>> obtenerPorConsultaMedica(@PathVariable Long consultaMedicaId) {
        List<RecetaDTO> lista = recetaRepository.findByConsultaMedicaId(consultaMedicaId).stream()
                .map(RecetaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
