package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.UsuarioDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.UsuarioRepository;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final ClinicaService clinicaService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public UsuarioDTO crear(@RequestBody UsuarioDTO dto) {
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Usuario usuario = dto.toEntity(clinica);
        Usuario guardado = service.crear(usuario);
        return UsuarioDTO.fromEntity(guardado);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listar().stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        return UsuarioDTO.fromEntity(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        Usuario usuario = dto.toEntity(clinica);
        Usuario guardado = service.actualizar(id, usuario);
        return UsuarioDTO.fromEntity(guardado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/username/{username}")
    public UsuarioDTO buscarPorUsername(@PathVariable String username) {
        return usuarioRepository.findByUsername(username)
                .map(UsuarioDTO::fromEntity)
                .orElse(null);
    }

    @GetMapping("/correo/{correo}")
    public UsuarioDTO buscarPorCorreo(@PathVariable String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(UsuarioDTO::fromEntity)
                .orElse(null);
    }

    @GetMapping("/rol/{rol}")
    public List<UsuarioDTO> buscarPorRol(@PathVariable String rol) {
        return usuarioRepository.findByRol(
                Grupo4.ProyectoDesarrollo.model.enums.Rol.valueOf(rol)).stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/clinica/{clinicaId}")
    public List<UsuarioDTO> buscarPorClinica(@PathVariable Long clinicaId) {
        return usuarioRepository.findByClinicaId(clinicaId).stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }
}