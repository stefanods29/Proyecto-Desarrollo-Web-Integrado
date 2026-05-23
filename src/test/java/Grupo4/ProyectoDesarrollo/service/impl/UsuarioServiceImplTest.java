package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import Grupo4.ProyectoDesarrollo.exception.DuplicateResourceException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("caleb");
        usuario.setPassword("raw_password");
        usuario.setCorreo("caleb@gmail.com");
    }

    @Test
    void testCrear() {
        when(repository.existsByUsername("caleb")).thenReturn(false);
        when(repository.existsByCorreo("caleb@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("raw_password")).thenReturn("hashed_password");
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario result = service.crear(usuario);

        assertNotNull(result);
        assertEquals("caleb", result.getUsername());
        assertEquals("hashed_password", result.getPassword());
        verify(repository).save(usuario);
    }

    @Test
    void testCrearUsernameDuplicado() {
        when(repository.existsByUsername("caleb")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.crear(usuario));
        verify(repository, never()).save(any());
    }

    @Test
    void testCrearCorreoDuplicado() {
        when(repository.existsByUsername("caleb")).thenReturn(false);
        when(repository.existsByCorreo("caleb@gmail.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.crear(usuario));
        verify(repository, never()).save(any());
    }

    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> lista = service.listar();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = service.buscarPorId(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void testActualizar() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setUsername("old_user");
        existente.setCorreo("old_mail@gmail.com");

        Usuario nuevo = new Usuario();
        nuevo.setUsername("new_user");
        nuevo.setCorreo("new_mail@gmail.com");
        nuevo.setPassword("new_raw_pwd");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.existsByUsername("new_user")).thenReturn(false);
        when(repository.existsByCorreo("new_mail@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("new_raw_pwd")).thenReturn("hashed_new_pwd");
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario actualizado = service.actualizar(1L, nuevo);

        assertNotNull(actualizado);
        assertEquals("new_user", actualizado.getUsername());
        assertEquals("hashed_new_pwd", actualizado.getPassword());
    }

    @Test
    void testEliminar() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(repository).delete(usuario);

        service.eliminar(1L);

        verify(repository).delete(usuario);
    }

    @Test
    void testBuscarPorUsername() {
        when(repository.findByUsername("caleb")).thenReturn(Optional.of(usuario));

        Usuario result = service.buscarPorUsername("caleb");

        assertNotNull(result);
        assertEquals("caleb", result.getUsername());
    }

    @Test
    void testBuscarPorUsernameInexistente() {
        when(repository.findByUsername("non_existent")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorUsername("non_existent"));
    }
}