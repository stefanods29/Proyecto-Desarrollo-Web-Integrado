package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.dto.RecepcionistaDTO;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Recepcionista;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.model.enums.Rol;
import Grupo4.ProyectoDesarrollo.repository.ClinicaRepository;
import Grupo4.ProyectoDesarrollo.repository.RecepcionistaRepository;
import Grupo4.ProyectoDesarrollo.repository.UsuarioRepository;
import Grupo4.ProyectoDesarrollo.service.RecepcionistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecepcionistaServiceImpl implements RecepcionistaService {

    private final RecepcionistaRepository recepcionistaRepository;
    private final ClinicaRepository clinicaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<RecepcionistaDTO> findAll() {
        return recepcionistaRepository.findAll().stream()
                .map(RecepcionistaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RecepcionistaDTO findById(Long id) {
        Recepcionista r = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recepcionista no encontrado"));
        return RecepcionistaDTO.fromEntity(r);
    }

    @Override
    @Transactional
    public RecepcionistaDTO save(RecepcionistaDTO dto) {
        Clinica clinica = clinicaRepository.findById(dto.getClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException("Clínica no encontrada"));

        // 1. Crear el usuario para el Login
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Rol.RECEPCIONISTA); // Enum
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // 2. Crear al Recepcionista y enlazar
        Recepcionista recepcionista = Recepcionista.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .dni(dto.getDni())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .clinica(clinica)
                .usuario(savedUsuario)
                .build();

        return RecepcionistaDTO.fromEntity(recepcionistaRepository.save(recepcionista));
    }

    @Override
    @Transactional
    public RecepcionistaDTO update(Long id, RecepcionistaDTO dto) {
        Recepcionista existente = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recepcionista no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setDni(dto.getDni());
        existente.setTelefono(dto.getTelefono());
        existente.setCorreo(dto.getCorreo());

        // Actualizar contraseña si Angular manda una nueva
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existente.getUsuario().setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return RecepcionistaDTO.fromEntity(recepcionistaRepository.save(existente));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Recepcionista r = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recepcionista no encontrado"));
        recepcionistaRepository.delete(r);
    }

    @Override
    public List<RecepcionistaDTO> findByClinica(Long clinicaId) {
        return recepcionistaRepository.findByClinicaId(clinicaId).stream()
                .map(RecepcionistaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RecepcionistaDTO findByUsername(String username) {
        Recepcionista r = recepcionistaRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Recepcionista no encontrado"));
        return RecepcionistaDTO.fromEntity(r);
    }
}