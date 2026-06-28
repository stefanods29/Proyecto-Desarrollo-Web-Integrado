package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ConsultaMedicaDTO;
import Grupo4.ProyectoDesarrollo.dto.MedicoRankingDTO;
import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.repository.ConsultaMedicaRepository;
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
    private final ConsultaMedicaRepository consultaMedicaRepository;

    @GetMapping
    public ResponseEntity<List<ConsultaMedicaDTO>> listar() {
        List<ConsultaMedicaDTO> lista = servicio.findAll().stream()
                .map(ConsultaMedicaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaMedicaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ConsultaMedicaDTO.fromEntity(servicio.findById(id)));
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
        return ResponseEntity.ok(ConsultaMedicaDTO.fromEntity(servicio.update(id, consulta)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConsultaMedicaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        List<ConsultaMedicaDTO> lista = consultaMedicaRepository.findByPacienteId(pacienteId).stream()
                .map(ConsultaMedicaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<ConsultaMedicaDTO>> obtenerPorMedico(@PathVariable Long medicoId) {
        List<ConsultaMedicaDTO> lista = consultaMedicaRepository.findByMedicoId(medicoId).stream()
                .map(ConsultaMedicaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/historia/{historiaClinicaId}")
    public ResponseEntity<List<ConsultaMedicaDTO>> obtenerPorHistoriaClinica(@PathVariable Long historiaClinicaId) {
        List<ConsultaMedicaDTO> lista = consultaMedicaRepository.findByHistoriaClinicaId(historiaClinicaId).stream()
                .map(ConsultaMedicaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/cita/{citaId}")
public ResponseEntity<ConsultaMedicaDTO> obtenerPorCita(@PathVariable Long citaId) {

    return consultaMedicaRepository.findByCitaId(citaId)
            .map(consulta -> ResponseEntity.ok(ConsultaMedicaDTO.fromEntity(consulta)))
            .orElse(ResponseEntity.notFound().build());
}

    @GetMapping("/ranking-medicos")
    public ResponseEntity<List<MedicoRankingDTO>> obtenerRankingMedicos() {
        List<MedicoRankingDTO> ranking = consultaMedicaRepository.obtenerRankingMedicosPorConsultas().stream()
                .map(MedicoRankingDTO::fromObjectArray)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ranking);
    }
}