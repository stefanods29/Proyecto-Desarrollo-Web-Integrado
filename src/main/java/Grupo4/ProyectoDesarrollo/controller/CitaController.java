package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.CitaDTO;
import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.service.CitaService;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.ConsultorioService;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService service;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final ConsultorioService consultorioService;
    private final ClinicaService clinicaService;

    @GetMapping
    public List<CitaDTO> listarCitas() {
        return service.listar().stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CitaDTO obtenerCita(@PathVariable Long id) {
        Cita cita = service.buscarPorId(id);
        return CitaDTO.fromEntity(cita);
    }

    @PostMapping
    public CitaDTO crearCita(@RequestBody CitaDTO dto) {
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Consultorio consultorio = dto.getConsultorioId() != null ? consultorioService.buscarPorId(dto.getConsultorioId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Cita cita = dto.toEntity(paciente, medico, consultorio, clinica);
        Cita guardada = service.crear(cita);
        return CitaDTO.fromEntity(guardada);
    }

    @PutMapping("/{id}")
    public CitaDTO actualizarCita(@PathVariable Long id, @RequestBody CitaDTO dto) {
        dto.setId(id);
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Consultorio consultorio = dto.getConsultorioId() != null ? consultorioService.buscarPorId(dto.getConsultorioId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Cita cita = dto.toEntity(paciente, medico, consultorio, clinica);
        Cita guardada = service.crear(cita);
        return CitaDTO.fromEntity(guardada);
    }

    @DeleteMapping("/{id}")
    public String eliminarCita(@PathVariable Long id) {
        service.eliminar(id);
        return "Cita eliminada";
    }
}
