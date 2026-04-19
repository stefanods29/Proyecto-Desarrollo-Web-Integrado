package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.medicoDTO;
import Grupo4.ProyectoDesarrollo.service.MedicoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class medicoController {

    private final MedicoService medicoService;

    public medicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public ResponseEntity<List<medicoDTO>> listarMedicos() {
        return ResponseEntity.ok(medicoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<medicoDTO> obtenerMedico(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.obtener(id));
    }

    @PostMapping
    public ResponseEntity<medicoDTO> crearMedico(@RequestBody medicoDTO medico) {

        System.out.println("==== DEBUG ====");
        System.out.println("NOMBRE: " + medico.getNombre());
        System.out.println("TELEFONO: " + medico.getTelefono());
        System.out.println("CORREO: " + medico.getCorreo());

        return ResponseEntity.ok(medicoService.crear(medico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<medicoDTO> actualizarMedico(
            @PathVariable Long id,
            @Valid @RequestBody medicoDTO medicoActualizado) {
        return ResponseEntity.ok(medicoService.actualizar(id, medicoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMedico(@PathVariable Long id) {
        medicoService.eliminar(id);
        return ResponseEntity.ok("Medico eliminado correctamente");
    }
}