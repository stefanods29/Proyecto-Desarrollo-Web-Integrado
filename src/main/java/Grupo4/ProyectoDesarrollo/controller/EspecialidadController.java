package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.EspecialidadDTO;
import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService service;

    @PostMapping
    public EspecialidadDTO crear(@RequestBody EspecialidadDTO dto) {
        Especialidad especialidad = dto.toEntity();
        Especialidad guardada = service.crear(especialidad);
        return EspecialidadDTO.fromEntity(guardada);
    }

    @GetMapping
    public List<EspecialidadDTO> listar() {
        return service.listar().stream()
                .map(EspecialidadDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EspecialidadDTO buscarPorId(@PathVariable Long id) {
        return EspecialidadDTO.fromEntity(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public EspecialidadDTO actualizar(@PathVariable Long id,
                                    @RequestBody EspecialidadDTO dto) {
        dto.setId(id);
        Especialidad especialidad = dto.toEntity();
        Especialidad guardada = service.crear(especialidad);
        return EspecialidadDTO.fromEntity(guardada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}