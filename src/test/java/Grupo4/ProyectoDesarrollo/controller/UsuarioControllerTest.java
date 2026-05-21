package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.dto.UsuarioDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;
    @Mock
    private ClinicaService clinicaService;

    @InjectMocks
    private UsuarioController controller;

    private Clinica clinicaMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(2L);
    }

    @Test
    void testCrear() {
        Usuario usuario = new Usuario();
        usuario.setUsername("caleb");
        usuario.setClinica(clinicaMock);

        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO dto = UsuarioDTO.fromEntity(usuario);
        UsuarioDTO result = controller.crear(dto);

        assertNotNull(result);
        assertEquals("caleb", result.getUsername());
    }

    @Test
    void testListar() {
        Usuario usuario = new Usuario();
        usuario.setClinica(clinicaMock);
        when(service.listar()).thenReturn(List.of(usuario));

        List<UsuarioDTO> lista = controller.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setClinica(clinicaMock);

        when(service.buscarPorId(1L)).thenReturn(usuario);

        UsuarioDTO result = controller.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testActualizar() {
        Usuario usuario = new Usuario();
        usuario.setUsername("nuevo");
        usuario.setClinica(clinicaMock);

        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.actualizar(eq(1L), any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO dto = UsuarioDTO.fromEntity(usuario);
        UsuarioDTO result = controller.actualizar(1L, dto);

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