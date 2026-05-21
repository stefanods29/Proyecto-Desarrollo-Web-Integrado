package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ConsultaMedicaDTO;
import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consulta-medica")
@RequiredArgsConstructor
public class ConsultaMedicaController {

    private final ConsultaMedicaServicio servicio;
    private final HistoriaClinicaServicio historiaClinicaServicio;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final CitaService citaService;
    private final ClinicaService clinicaService;

    @GetMapping
    public ResponseEntity<List<ConsultaMedicaDTO>> listar() {
        List<ConsultaMedicaDTO> lista = servicio.findAll().stream()
                .map(ConsultaMedicaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaMedicaDTO> obtener(@PathVariable Long id) {
        ConsultaMedica consulta = servicio.findById(id);
        if (consulta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ConsultaMedicaDTO.fromEntity(consulta));
    }

    @PostMapping
    public ResponseEntity<ConsultaMedicaDTO> crear(@RequestBody ConsultaMedicaDTO dto) {
        HistoriaClinica hc = dto.getHistoriaClinicaId() != null ? historiaClinicaServicio.buscarPorId(dto.getHistoriaClinicaId()) : null;
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Cita cita = dto.getCitaId() != null ? citaService.buscarPorId(dto.getCitaId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;

        ConsultaMedica consulta = dto.toEntity(hc, paciente, medico, cita, clinica);
        ConsultaMedica creada = servicio.save(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ConsultaMedicaDTO.fromEntity(creada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaMedicaDTO> actualizar(@PathVariable Long id, @RequestBody ConsultaMedicaDTO dto) {
        dto.setId(id);
        HistoriaClinica hc = dto.getHistoriaClinicaId() != null ? historiaClinicaServicio.buscarPorId(dto.getHistoriaClinicaId()) : null;
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Cita cita = dto.getCitaId() != null ? citaService.buscarPorId(dto.getCitaId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;

        ConsultaMedica consulta = dto.toEntity(hc, paciente, medico, cita, clinica);
        ConsultaMedica actualizada = servicio.update(id, consulta);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ConsultaMedicaDTO.fromEntity(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ConsultaMedica existente = servicio.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        servicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}