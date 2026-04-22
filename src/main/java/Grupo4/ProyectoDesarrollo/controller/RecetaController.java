package Grupo4.ProyectoDesarrollo.controller;


import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaServicio servicio;

    public RecetaController(RecetaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
        public ResponseEntity<List<Receta>> listar() {
            return ResponseEntity.ok(servicio.findAll());
        }
        @GetMapping("/{id}")
        public ResponseEntity<Receta> obtener(@PathVariable Long id) {
            Receta obte = servicio.findById(id);
            if (obte == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(obte);
        }

        @PostMapping
        public ResponseEntity<Receta> crear(@RequestBody Receta obte) {
            return ResponseEntity.ok(servicio.save(obte));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Receta> actualizar(@PathVariable Long id, @RequestBody Receta obte) {
            Receta actualizado = servicio.update(id, obte);
            if (actualizado == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(actualizado);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminar(@PathVariable Long id) {
            servicio.delete(id);
            return ResponseEntity.ok("Receta eliminada");
        }
    }
