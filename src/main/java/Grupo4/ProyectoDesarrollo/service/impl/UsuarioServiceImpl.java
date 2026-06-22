package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.DuplicateResourceException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.UsuarioRepository;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario crear(Usuario usuario) {
        if (repository.existsByUsername(usuario.getUsername())) {
            throw new DuplicateResourceException(
                    "El nombre de usuario '" + usuario.getUsername() + "' ya está registrado");
        }

        if (repository.existsByCorreo(usuario.getCorreo())) {
            throw new DuplicateResourceException(
                    "El correo '" + usuario.getCorreo() + "' ya está registrado");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }
    
        return repository.save(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        if (!existente.getUsername().equalsIgnoreCase(usuario.getUsername()) &&
                repository.existsByUsername(usuario.getUsername())) {
            throw new DuplicateResourceException(
                    "El nombre de usuario '" + usuario.getUsername() + "' ya está registrado");
        }
        if (!existente.getCorreo().equalsIgnoreCase(usuario.getCorreo()) &&
                repository.existsByCorreo(usuario.getCorreo())) {
            throw new DuplicateResourceException("El correo '" + usuario.getCorreo() + "' ya está registrado");
        }

        existente.setUsername(usuario.getUsername());
        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
            existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        existente.setNombre(usuario.getNombre());
        existente.setApellido(usuario.getApellido());
        existente.setCorreo(usuario.getCorreo());
        existente.setTelefono(usuario.getTelefono());
        existente.setRol(usuario.getRol());
        existente.setActivo(usuario.getActivo());
        existente.setClinica(usuario.getClinica());

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        repository.delete(existente);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + username));
    }
}