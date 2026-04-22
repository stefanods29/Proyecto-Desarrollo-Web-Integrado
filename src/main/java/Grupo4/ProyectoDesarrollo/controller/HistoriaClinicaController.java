package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.service.HistoriaClinicaServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historia-clinica")
public class HistoriaClinicaController {

    private final HistoriaClinicaServicio servicio;

    public HistoriaClinicaController(HistoriaClinicaServicio servicio) {
        this.servicio = servicio;
    }


    @GetMapping
    public ResponseEntity<List<HistoriaClinica>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinica> obtener(@PathVariable Long id) {
        HistoriaClinica obte = servicio.findById(id);
        if (obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obte);
    }

    @PostMapping
    public ResponseEntity<HistoriaClinica> crear(@RequestBody HistoriaClinica obte) {
        return ResponseEntity.ok(servicio.save(obte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinica> actualizar(@PathVariable Long id, @RequestBody HistoriaClinica obte) {
        HistoriaClinica actualizado = servicio.update(id, obte);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Historia clínica eliminada");
    }
}
