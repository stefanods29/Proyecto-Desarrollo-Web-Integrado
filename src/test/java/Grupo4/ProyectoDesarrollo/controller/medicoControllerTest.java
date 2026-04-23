package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.service.MedicoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

    class MedicoControllerTest {

    @Mock
    private MedicoService service;

    @InjectMocks
    private MedicoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        Medico medico = new Medico();
        medico.setId(1L);

        when(service.crear(medico)).thenReturn(medico);

        Medico result = controller.crear(medico);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testListar() {
        when(service.listar()).thenReturn(List.of(new Medico()));

        List<Medico> lista = controller.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        Medico medico = new Medico();
        medico.setId(1L);

        when(service.buscarPorId(1L)).thenReturn(medico);

        Medico result = controller.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testActualizar() {
        Medico medico = new Medico();
        medico.setId(1L);

        when(service.crear(medico)).thenReturn(medico);

        Medico result = controller.actualizar(1L, medico);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testEliminar() {
        doNothing().when(service).eliminar(1L);

        controller.eliminar(1L);

        verify(service, times(1)).eliminar(1L);
    }
}