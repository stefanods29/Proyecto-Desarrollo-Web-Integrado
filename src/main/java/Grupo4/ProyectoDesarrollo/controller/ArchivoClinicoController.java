package Grupo4.ProyectoDesarrollo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Grupo4.ProyectoDesarrollo.dto.ArchivoClinicoDTO;
import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.repository.ArchivoClinicoRepository;
import Grupo4.ProyectoDesarrollo.service.ArchivoClinicoServicio;
import Grupo4.ProyectoDesarrollo.service.ConsultaMedicaServicio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/archivos")
@RequiredArgsConstructor
public class ArchivoClinicoController {

    private final ArchivoClinicoServicio servicio;
    private final ConsultaMedicaServicio consultaMedicaServicio;
    private final ArchivoClinicoRepository archivoClinicoRepository;

    @GetMapping
    public ResponseEntity<List<ArchivoClinicoDTO>> listar() {
        List<ArchivoClinicoDTO> lista = servicio.findAll().stream()
                .map(ArchivoClinicoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchivoClinicoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ArchivoClinicoDTO.fromEntity(servicio.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ArchivoClinicoDTO> crear(@RequestBody ArchivoClinicoDTO dto) {
        ConsultaMedica cm = dto.getConsultaMedicaId() != null
                ? consultaMedicaServicio.findById(dto.getConsultaMedicaId())
                : null;
        ArchivoClinico ac = dto.toEntity(cm);
        ArchivoClinico guardado = servicio.save(ac);
        return ResponseEntity.ok(ArchivoClinicoDTO.fromEntity(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArchivoClinicoDTO> actualizar(@PathVariable Long id, @RequestBody ArchivoClinicoDTO dto) {
        dto.setId(id);
        ConsultaMedica cm = dto.getConsultaMedicaId() != null
                ? consultaMedicaServicio.findById(dto.getConsultaMedicaId())
                : null;
        ArchivoClinico ac = dto.toEntity(cm);
        return ResponseEntity.ok(ArchivoClinicoDTO.fromEntity(servicio.update(id, ac)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicio.delete(id);
        return ResponseEntity.ok("Archivo Eliminado");
    }

    @GetMapping("/consulta/{consultaMedicaId}")
    public ResponseEntity<List<ArchivoClinicoDTO>> obtenerPorConsultaMedica(@PathVariable Long consultaMedicaId) {
        List<ArchivoClinicoDTO> lista = archivoClinicoRepository.findByConsultaMedicaId(consultaMedicaId).stream()
                .map(ArchivoClinicoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ArchivoClinicoDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        List<ArchivoClinicoDTO> lista = archivoClinicoRepository.buscarArchivosPorPacienteId(pacienteId).stream()
                .map(ArchivoClinicoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/mis-archivos")
    public ResponseEntity<List<ArchivoClinicoDTO>> obtenerMisArchivos() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<ArchivoClinico> misArchivos = servicio.buscarPorPacienteUsername(username);
        List<ArchivoClinicoDTO> dtos = misArchivos.stream()
                .map(ArchivoClinicoDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
