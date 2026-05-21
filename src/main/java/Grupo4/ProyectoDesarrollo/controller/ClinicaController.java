package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ClinicaDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clinicas")
@RequiredArgsConstructor
public class ClinicaController {

    private final ClinicaService service;

    @PostMapping
    public ClinicaDTO crear(@RequestBody ClinicaDTO dto) {
        Clinica clinica = dto.toEntity();
        Clinica guardada = service.crear(clinica);
        return ClinicaDTO.fromEntity(guardada);
    }

    @GetMapping
    public List<ClinicaDTO> listar() {
        return service.listar().stream()
                .map(ClinicaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClinicaDTO buscarPorId(@PathVariable Long id) {
        return ClinicaDTO.fromEntity(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ClinicaDTO actualizar(@PathVariable Long id, @RequestBody ClinicaDTO dto) {
        dto.setId(id);
        Clinica clinica = dto.toEntity();
        Clinica guardada = service.crear(clinica);
        return ClinicaDTO.fromEntity(guardada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
