package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ArchivoClinicoDTO;
import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.service.ArchivoClinicoServicio;
import Grupo4.ProyectoDesarrollo.service.ConsultaMedicaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/archivos")
@RequiredArgsConstructor
public class ArchivoClinicoController {

    private final ArchivoClinicoServicio servicio;
    private final ConsultaMedicaServicio consultaMedicaServicio;

    @GetMapping
    public ResponseEntity<List<ArchivoClinicoDTO>> listar(){
        List<ArchivoClinicoDTO> lista = servicio.findAll().stream()
                .map(ArchivoClinicoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchivoClinicoDTO> obtener(@PathVariable Long id){
        return ResponseEntity.ok(ArchivoClinicoDTO.fromEntity(servicio.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ArchivoClinicoDTO> crear(@RequestBody ArchivoClinicoDTO dto){
        ConsultaMedica cm = dto.getConsultaMedicaId() != null ? consultaMedicaServicio.findById(dto.getConsultaMedicaId()) : null;
        ArchivoClinico ac = dto.toEntity(cm);
        ArchivoClinico guardado = servicio.save(ac);
        return ResponseEntity.ok(ArchivoClinicoDTO.fromEntity(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArchivoClinicoDTO> actualizar (@PathVariable Long id, @RequestBody ArchivoClinicoDTO dto){
        dto.setId(id);
        ConsultaMedica cm = dto.getConsultaMedicaId() != null ? consultaMedicaServicio.findById(dto.getConsultaMedicaId()) : null;
        ArchivoClinico ac = dto.toEntity(cm);
        return ResponseEntity.ok(ArchivoClinicoDTO.fromEntity(servicio.update(id, ac)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar (@PathVariable Long id){
        servicio.delete(id);
        return ResponseEntity.ok("Archivo Eliminado");
    }
}
