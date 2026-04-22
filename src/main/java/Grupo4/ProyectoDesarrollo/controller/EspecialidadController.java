package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService service;

    @PostMapping
    public Especialidad crear(@RequestBody Especialidad especialidad) {
        return service.crear(especialidad);
    }

    @GetMapping
    public List<Especialidad> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Especialidad buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Especialidad actualizar(@PathVariable Long id,
                                    @RequestBody Especialidad especialidad) {
        especialidad.setId(id);
        return service.crear(especialidad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}