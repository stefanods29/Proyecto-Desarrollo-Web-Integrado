package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.service.PacienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class PacienteControllerTest {

    @Mock
    private PacienteService service;

    @InjectMocks
    private PacienteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");

        when(service.crear(paciente)).thenReturn(paciente);

        Paciente result = controller.crear(paciente);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void testListar() {
        when(service.listar()).thenReturn(List.of(new Paciente()));

        List<Paciente> lista = controller.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        when(service.buscarPorId(1L)).thenReturn(paciente);

        Paciente result = controller.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testActualizar() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Actualizado");

        when(service.crear(paciente)).thenReturn(paciente);

        Paciente result = controller.actualizar(1L, paciente);

        assertNotNull(result);
        assertEquals("Actualizado", result.getNombre());
    }

    @Test
    void testEliminar() {
        doNothing().when(service).eliminar(1L);

        controller.eliminar(1L);

        verify(service, times(1)).eliminar(1L);
    }
}