package Grupo4.ProyectoDesarrollo.controller;


import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.service.ArchivoClinicoServicio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoClinicoController {

    private final ArchivoClinicoServicio servicio;

    public ArchivoClinicoController(ArchivoClinicoServicio servicio) {
        this.servicio = servicio;
    }


    @GetMapping
    public ResponseEntity<List<ArchivoClinico>> listar(){
        return ResponseEntity.ok(servicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchivoClinico> obtener(@PathVariable Long id){
        ArchivoClinico obte = servicio.findById(id);
        if(obte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(obte);
    }

    @PostMapping
    public ResponseEntity<ArchivoClinico> crear(@RequestBody ArchivoClinico obte){
        return ResponseEntity.ok(servicio.save(obte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArchivoClinico> actualizar (@PathVariable Long id, @RequestBody ArchivoClinico obte){
        ArchivoClinico actualizado = servicio.update(id, obte);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar (@PathVariable Long id){
        servicio.delete(id);
        return ResponseEntity.ok("Archivo Eliminado");
    }
}
