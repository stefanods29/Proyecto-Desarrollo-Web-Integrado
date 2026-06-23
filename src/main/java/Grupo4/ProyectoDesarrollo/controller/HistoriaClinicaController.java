package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.HistoriaClinicaDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.repository.HistoriaClinicaRepository;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.HistoriaClinicaServicio;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historia-clinica")
@RequiredArgsConstructor
public class HistoriaClinicaController {

    private final HistoriaClinicaServicio servicio;
    private final PacienteService pacienteService;
    private final ClinicaService clinicaService;
    private final HistoriaClinicaRepository historiaClinicaRepository;

    @GetMapping
    public ResponseEntity<List<HistoriaClinicaDTO>> listar() {
        List<HistoriaClinicaDTO> lista = servicio.findAll().stream()
                .map(HistoriaClinicaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinicaDTO> obtener(@PathVariable Long id) {
        HistoriaClinica obte = servicio.buscarPorId(id);
        if (obte == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(HistoriaClinicaDTO.fromEntity(obte));
    }

    @PostMapping
    public ResponseEntity<HistoriaClinicaDTO> crear(@RequestBody HistoriaClinicaDTO dto) {
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        HistoriaClinica hc = dto.toEntity(paciente, clinica);
        HistoriaClinica guardada = servicio.save(hc);
        return ResponseEntity.ok(HistoriaClinicaDTO.fromEntity(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaDTO> actualizar(@PathVariable Long id, @RequestBody HistoriaClinicaDTO dto) {
        dto.setId(id);
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        HistoriaClinica hc = dto.toEntity(paciente, clinica);
        HistoriaClinica actualizado = servicio.update(id, hc);
        if (actualizado == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(HistoriaClinicaDTO.fromEntity(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Historia clínica eliminada");
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinicaDTO> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return historiaClinicaRepository.findByPacienteId(pacienteId)
                .map(hc -> ResponseEntity.ok(HistoriaClinicaDTO.fromEntity(hc)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mi-historial")
    public ResponseEntity<HistoriaClinicaDTO> obtenerMiHistorial() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        HistoriaClinica miHistoria = servicio.buscarPorPacienteUsername(username);
        if (miHistoria == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(HistoriaClinicaDTO.fromEntity(miHistoria));
    }
}
