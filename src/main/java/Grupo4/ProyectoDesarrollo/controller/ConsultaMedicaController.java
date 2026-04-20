package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.servicio.ConsultaMedicaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consulta-medica")
public class ConsultaMedicaController {

    private final ConsultaMedicaServicio servicio;

    public ConsultaMedicaController(ConsultaMedicaServicio servicio) {
        this.servicio = servicio;
    }


    @GetMapping
    public ResponseEntity<List<ConsultaMedica>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaMedica> obtener(@PathVariable Long id) {
        ConsultaMedica obte = servicio.findById(id);
        if (obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obte);
    }

    @PostMapping
    public ResponseEntity<ConsultaMedica> crear(@RequestBody ConsultaMedica obte) {
        return ResponseEntity.ok(servicio.save(obte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaMedica> actualizar(@PathVariable Long id, @RequestBody ConsultaMedica obte) {
        ConsultaMedica actualizado = servicio.update(id, obte);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Consulta eliminada");
    }
}

