package Grupo4.ProyectoDesarrollo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Grupo4.ProyectoDesarrollo.dto.CitaDTO;
import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import Grupo4.ProyectoDesarrollo.service.CitaService;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.ConsultorioService;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService service;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final ConsultorioService consultorioService;
    private final ClinicaService clinicaService;
    private final CitaRepository citaRepository;

    // 🔥 NUEVO ENDPOINT: Obtiene estrictamente las citas del paciente logueado
    @GetMapping("/mis-citas")
    public ResponseEntity<List<CitaDTO>> obtenerMisCitas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Cita> misCitas = service.buscarPorPacienteUsername(username);

        List<CitaDTO> dtos = misCitas.stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

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
        Consultorio consultorio = dto.getConsultorioId() != null
                ? consultorioService.buscarPorId(dto.getConsultorioId())
                : null;
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
        Consultorio consultorio = dto.getConsultorioId() != null
                ? consultorioService.buscarPorId(dto.getConsultorioId())
                : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Cita cita = dto.toEntity(paciente, medico, consultorio, clinica);
        Cita guardada = service.crear(cita);
        return CitaDTO.fromEntity(guardada);
    }

    @PatchMapping("/{id}/estado")
    public CitaDTO cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        CitaEstado estado = CitaEstado.valueOf(body.get("estado"));

        Cita cita = service.cambiarEstado(id, estado);

        return CitaDTO.fromEntity(cita);
    }

    @DeleteMapping("/{id}")
    public String eliminarCita(@PathVariable Long id) {
        service.eliminar(id);
        return "Cita eliminada";
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<CitaDTO> obtenerCitasPaciente(@PathVariable Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId).stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/medico/{medicoId}")
    public List<CitaDTO> obtenerCitasMedico(@PathVariable Long medicoId) {
        return citaRepository.findByMedicoId(medicoId).stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/estado/{estado}")
    public List<CitaDTO> obtenerCitasPorEstado(@PathVariable String estado) {
        return citaRepository.findByEstado(
                Grupo4.ProyectoDesarrollo.model.enums.CitaEstado.valueOf(estado)).stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/contar/estado/{estado}")
    public long contarCitasPorEstado(@PathVariable String estado) {
        return citaRepository.countByEstado(
                Grupo4.ProyectoDesarrollo.model.enums.CitaEstado.valueOf(estado));
    }

    @GetMapping("/medico/{medicoId}/rango")
    public List<CitaDTO> obtenerCitasMedicoPorRangoFechas(
            @PathVariable Long medicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return service.buscarPorMedicoYFechaHoraRango(medicoId, inicio, fin).stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/clinica/{clinicaId}/rango")
    public List<CitaDTO> obtenerCitasClinicaPorRangoFechas(
            @PathVariable Long clinicaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return service.buscarPorClinicaYFechaHoraRango(clinicaId, inicio, fin).stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/medico/{medicoId}/buscar-fecha")
    public List<CitaDTO> buscarCitasPorMedicoYFecha(
            @PathVariable Long medicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return service.buscarCitasPorMedicoYFecha(medicoId, inicio, fin).stream()
                .map(CitaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/clinica/{clinicaId}/contar")
    public long contarCitasPorClinica(@PathVariable Long clinicaId) {
        return service.contarCitasPorClinica(clinicaId);
    }
}