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

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId_OK() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(1L);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}