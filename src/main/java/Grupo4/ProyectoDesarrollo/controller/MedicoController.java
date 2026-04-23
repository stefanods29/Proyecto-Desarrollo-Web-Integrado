package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService service;

    @PostMapping
    public Medico crear(@RequestBody Medico medico) {
        return service.crear(medico);
    }

    @GetMapping
    public List<Medico> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Medico medico = service.buscarPorId(id);
        if (medico == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(medico);
    }

    @PutMapping("/{id}")
    public Medico actualizar(@PathVariable Long id,
                              @RequestBody Medico medico) {
        medico.setId(id);
        return service.crear(medico);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}