package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        Usuario usuario = new Usuario();
        usuario.setUsername("caleb");

        when(service.crear(usuario)).thenReturn(usuario);

        Usuario result = controller.crear(usuario);

        assertNotNull(result);
        assertEquals("caleb", result.getUsername());
    }

    @Test
    void testListar() {
        when(service.listar()).thenReturn(List.of(new Usuario()));

        List<Usuario> lista = controller.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(service.buscarPorId(1L)).thenReturn(usuario);

        Usuario result = controller.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
void testActualizar() {
    Usuario usuario = new Usuario();
    usuario.setUsername("nuevo");

    when(service.actualizar(eq(1L), any(Usuario.class))).thenReturn(usuario);

    Usuario result = controller.actualizar(1L, usuario);

    assertNotNull(result);
    assertEquals("nuevo", result.getUsername());
}
    @Test
    void testEliminar() {
        doNothing().when(service).eliminar(1L);

        controller.eliminar(1L);

        verify(service, times(1)).eliminar(1L);
    }
}