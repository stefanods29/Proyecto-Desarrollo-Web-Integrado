package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.service.ConsultaMedicaServicio;

import org.springframework.http.HttpStatus;
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
        List<ConsultaMedica> lista = servicio.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaMedica> obtener(@PathVariable Long id) {
        ConsultaMedica consulta = servicio.findById(id);
        if (consulta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consulta);
    }

    @PostMapping
    public ResponseEntity<ConsultaMedica> crear(@RequestBody ConsultaMedica consulta) {
        ConsultaMedica creada = servicio.save(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaMedica> actualizar(@PathVariable Long id, @RequestBody ConsultaMedica consulta) {
        ConsultaMedica actualizada = servicio.update(id, consulta);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ConsultaMedica existente = servicio.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        servicio.delete(id);
        return ResponseEntity.noContent().build();
    }
}