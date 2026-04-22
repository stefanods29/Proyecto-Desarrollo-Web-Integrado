package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.service.DetalleRecetaServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-receta")
public class DetalleRecetaController{

    private final DetalleRecetaServicio servicio;

    public DetalleRecetaController(DetalleRecetaServicio servicio) {
        this.servicio = servicio;
    }


    @GetMapping
    public ResponseEntity<List<DetalleReceta>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleReceta> obtener(@PathVariable Long id) {
        DetalleReceta obte = servicio.findById(id);
        if (obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obte);
    }

    @PostMapping
    public ResponseEntity<DetalleReceta> crear(@RequestBody DetalleReceta obte) {
        return ResponseEntity.ok(servicio.save(obte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleReceta> actualizar(@PathVariable Long id, @RequestBody DetalleReceta obte) {
        DetalleReceta actualizado = servicio.update(id, obte);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Detalle eliminado");
    }
}
