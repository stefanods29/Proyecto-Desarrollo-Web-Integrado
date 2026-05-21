package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.MedicoDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.EspecialidadService;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService service;
    private final UsuarioService usuarioService;
    private final EspecialidadService especialidadService;
    private final ClinicaService clinicaService;

    @PostMapping
    public MedicoDTO crear(@RequestBody MedicoDTO dto) {
        Usuario usuario = dto.getUsuarioId() != null ? usuarioService.buscarPorId(dto.getUsuarioId()) : null;
        Especialidad especialidad = dto.getEspecialidadId() != null ? especialidadService.buscarPorId(dto.getEspecialidadId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Medico medico = dto.toEntity(usuario, especialidad, clinica);
        Medico guardado = service.crear(medico);
        return MedicoDTO.fromEntity(guardado);
    }

    @GetMapping
    public List<MedicoDTO> listar() {
        return service.listar().stream()
                .map(MedicoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> buscarPorId(@PathVariable Long id) {
        Medico medico = service.buscarPorId(id);
        if (medico == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(MedicoDTO.fromEntity(medico));
    }

    @PutMapping("/{id}")
    public MedicoDTO actualizar(@PathVariable Long id,
                              @RequestBody MedicoDTO dto) {
        dto.setId(id);
        Usuario usuario = dto.getUsuarioId() != null ? usuarioService.buscarPorId(dto.getUsuarioId()) : null;
        Especialidad especialidad = dto.getEspecialidadId() != null ? especialidadService.buscarPorId(dto.getEspecialidadId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Medico medico = dto.toEntity(usuario, especialidad, clinica);
        Medico guardado = service.crear(medico);
        return MedicoDTO.fromEntity(guardado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}