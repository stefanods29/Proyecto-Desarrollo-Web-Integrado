package Grupo4.ProyectoDesarrollo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Grupo4.ProyectoDesarrollo.dto.PacienteDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;
    private final ClinicaService clinicaService;
    private final UsuarioService usuarioService;
    private final PacienteRepository pacienteRepository;

    // 🔥 NUEVO ENDPOINT PARA RECUPERAR EL PERFIL DEL USUARIO LOGUEADO
    @GetMapping("/perfil")
    public ResponseEntity<PacienteDTO> obtenerMiPerfil() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        Paciente paciente = service.buscarPorUsername(username);
        return ResponseEntity.ok(PacienteDTO.fromEntity(paciente));
    }

    @PostMapping
    public PacienteDTO crear(@RequestBody PacienteDTO dto) {
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Usuario usuario = dto.getUsuarioId() != null ? usuarioService.buscarPorId(dto.getUsuarioId()) : null;
        Paciente paciente = dto.toEntity(clinica, usuario);
        Paciente guardado = service.crear(paciente);
        return PacienteDTO.fromEntity(guardado);
    }

    @GetMapping
    public List<PacienteDTO> listar() {
        return service.listar().stream()
                .map(PacienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PacienteDTO buscarPorId(@PathVariable Long id) {
        return PacienteDTO.fromEntity(service.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public List<PacienteDTO> buscarPorNombre(@RequestParam String q) {
        return service.buscarPorNombreOApellido(q).stream()
                .map(PacienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public PacienteDTO actualizar(@PathVariable Long id, @RequestBody PacienteDTO dto) {
        dto.setId(id);
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Usuario usuario = dto.getUsuarioId() != null ? usuarioService.buscarPorId(dto.getUsuarioId()) : null;
        Paciente paciente = dto.toEntity(clinica, usuario);
        Paciente guardado = service.crear(paciente);
        return PacienteDTO.fromEntity(guardado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/documento/{numeroDocumento}")
    public PacienteDTO buscarPorDocumento(@PathVariable String numeroDocumento) {
        return PacienteDTO.fromEntity(service.buscarPorNumeroDocumento(numeroDocumento));
    }

    @GetMapping("/clinica/{clinicaId}")
    public List<PacienteDTO> buscarPorClinica(@PathVariable Long clinicaId) {
        return pacienteRepository.findByClinicaId(clinicaId).stream()
                .map(PacienteDTO::fromEntity)
                .collect(Collectors.toList());
    }
}