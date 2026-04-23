package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        Usuario usuario = new Usuario();
        usuario.setUsername("caleb");

        when(repository.save(usuario)).thenReturn(usuario);

        Usuario result = service.crear(usuario);

        assertNotNull(result);
        assertEquals("caleb", result.getUsername());
    }

    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(List.of(new Usuario()));

        List<Usuario> lista = service.listar();

        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = service.buscarPorId(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testActualizar() {
        Usuario existente = new Usuario();
        existente.setId(1L);

        Usuario nuevo = new Usuario();
        nuevo.setUsername("nuevo");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Usuario.class))).thenReturn(existente);

        Usuario actualizado = service.actualizar(1L, nuevo);

        assertNotNull(actualizado);
    }

    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }
}