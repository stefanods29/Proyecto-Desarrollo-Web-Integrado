package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.service.ConsultorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
@RequiredArgsConstructor
public class ConsultorioController {

    private final ConsultorioService service;

    @PostMapping
    public Consultorio crear(@RequestBody Consultorio consultorio) {
        return service.crear(consultorio);
    }

    @GetMapping
    public List<Consultorio> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Consultorio buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Consultorio actualizar(@PathVariable Long id, @RequestBody Consultorio consultorio) {
        consultorio.setId(id);
        return service.crear(consultorio);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}