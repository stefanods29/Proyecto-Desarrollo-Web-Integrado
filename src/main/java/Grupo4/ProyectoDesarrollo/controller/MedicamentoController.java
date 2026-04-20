package Grupo4.ProyectoDesarrollo.controller;
import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.servicio.MedicamentoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoServicio servicio;

    public MedicamentoController(MedicamentoServicio servicio) {
        this.servicio = servicio;
    }


    @GetMapping
    public ResponseEntity<List<Medicamento>> listar() {
        return ResponseEntity.ok(servicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> obtener(@PathVariable Long id) {
        Medicamento obte = servicio.findById(id);
        if (obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obte);
    }

    @PostMapping
    public ResponseEntity<Medicamento> crear(@RequestBody Medicamento obte) {
        return ResponseEntity.ok(servicio.save(obte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> actualizar(@PathVariable Long id, @RequestBody Medicamento obte) {
        Medicamento actualizado = servicio.update(id, obte);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Medicamento eliminado");
    }
}
