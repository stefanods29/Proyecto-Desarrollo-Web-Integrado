package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinicas")
@RequiredArgsConstructor
public class ClinicaController {

    private final ClinicaService service;

    @PostMapping
    public Clinica crear(@RequestBody Clinica clinica) {
        return service.crear(clinica);
    }

    @GetMapping
    public List<Clinica> listar() {
        return service.listar();
    }   

    @GetMapping("/{id}")
    public Clinica buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Clinica actualizar(@PathVariable Long id, @RequestBody Clinica clinica) {
        clinica.setId(id);
        return service.crear(clinica);
    }
}
