package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ConsultorioDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.ConsultorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultorios")
@RequiredArgsConstructor
public class ConsultorioController {

    private final ConsultorioService service;
    private final ClinicaService clinicaService;

    @PostMapping
    public ConsultorioDTO crear(@RequestBody ConsultorioDTO dto) {
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Consultorio consultorio = dto.toEntity(clinica);
        Consultorio guardado = service.crear(consultorio);
        return ConsultorioDTO.fromEntity(guardado);
    }

    @GetMapping
    public List<ConsultorioDTO> listar() {
        return service.listar().stream()
                .map(ConsultorioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ConsultorioDTO buscarPorId(@PathVariable Long id) {
        return ConsultorioDTO.fromEntity(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ConsultorioDTO actualizar(@PathVariable Long id, @RequestBody ConsultorioDTO dto) {
        dto.setId(id);
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Consultorio consultorio = dto.toEntity(clinica);
        Consultorio guardado = service.crear(consultorio);
        return ConsultorioDTO.fromEntity(guardado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}