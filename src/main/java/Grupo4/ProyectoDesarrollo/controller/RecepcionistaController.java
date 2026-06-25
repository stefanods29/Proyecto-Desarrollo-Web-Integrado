package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.RecepcionistaDTO;
import Grupo4.ProyectoDesarrollo.service.RecepcionistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recepcionistas")
@RequiredArgsConstructor
public class RecepcionistaController {

    private final RecepcionistaService recepcionistaService;

    // 🔥 ENDPOINT ESPECIAL: Obtiene el perfil de quien inició sesión
    @GetMapping("/mi-perfil")
    public ResponseEntity<RecepcionistaDTO> miPerfil() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        RecepcionistaDTO dto = recepcionistaService.findByUsername(username);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RecepcionistaDTO>> listar() {
        return ResponseEntity.ok(recepcionistaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionistaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recepcionistaService.findById(id));
    }

    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<RecepcionistaDTO>> obtenerPorClinica(@PathVariable Long clinicaId) {
        return ResponseEntity.ok(recepcionistaService.findByClinica(clinicaId));
    }

    @PostMapping
    public ResponseEntity<RecepcionistaDTO> crear(@RequestBody RecepcionistaDTO dto) {
        RecepcionistaDTO creado = recepcionistaService.save(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionistaDTO> actualizar(@PathVariable Long id, @RequestBody RecepcionistaDTO dto) {
        RecepcionistaDTO actualizado = recepcionistaService.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        recepcionistaService.delete(id);
        return ResponseEntity.ok("Recepcionista eliminado exitosamente");
    }
}