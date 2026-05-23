package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.FacturaDTO;
import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.service.CitaService;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.FacturaService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {
    private final FacturaService service;
    private final PacienteService pacienteService;
    private final CitaService citaService;
    private final ClinicaService clinicaService;

    @PostMapping
    public ResponseEntity<FacturaDTO> crear(@RequestBody FacturaDTO dto) {
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Cita cita = dto.getCitaId() != null ? citaService.buscarPorId(dto.getCitaId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;

        Factura factura = dto.toEntity(paciente, cita, clinica);
        Factura guardada = service.crear(factura);
        return ResponseEntity.ok(FacturaDTO.fromEntity(guardada));
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> listar() {
        List<FacturaDTO> lista = service.listar().stream()
                .map(FacturaDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(FacturaDTO.fromEntity(service.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaDTO> actualizar(@PathVariable Long id, @RequestBody FacturaDTO dto) {
        dto.setId(id);
        Paciente paciente = dto.getPacienteId() != null ? pacienteService.buscarPorId(dto.getPacienteId()) : null;
        Cita cita = dto.getCitaId() != null ? citaService.buscarPorId(dto.getCitaId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;

        Factura factura = dto.toEntity(paciente, cita, clinica);
        Factura actualizada = service.crear(factura);
        return ResponseEntity.ok(FacturaDTO.fromEntity(actualizada));
    }
}
