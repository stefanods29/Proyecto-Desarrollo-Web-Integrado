package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.service.ConsultorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultorioControllerTest {

    @Mock
    private ConsultorioService service;

    @InjectMocks
    private ConsultorioController controller;

    private Consultorio consultorioMock;

    @BeforeEach
    void setUp() {
        consultorioMock = new Consultorio();
        consultorioMock.setId(1L);
    }

    @Test
    void crearOk() {
        when(service.crear(any(Consultorio.class))).thenReturn(consultorioMock);

        Consultorio resultado = controller.crear(new Consultorio());

        assertEquals(consultorioMock, resultado);
        verify(service, times(1)).crear(any(Consultorio.class));
    }

    @Test
    void listarOk() {
        List<Consultorio> listaEsperada = Arrays.asList(consultorioMock, new Consultorio());
        when(service.listar()).thenReturn(listaEsperada);

        List<Consultorio> resultado = controller.listar();

        assertEquals(2, resultado.size());
        assertEquals(listaEsperada, resultado);
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorIdOk() {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(consultorioMock);

        Consultorio resultado = controller.buscarPorId(id);

        assertEquals(consultorioMock, resultado);
        verify(service, times(1)).buscarPorId(id);
    }

    @Test
    void actualizarOk() {
        Long id = 2L;
        Consultorio consultorioAActualizar = new Consultorio();
        when(service.crear(any(Consultorio.class))).thenReturn(consultorioMock);

        Consultorio resultado = controller.actualizar(id, consultorioAActualizar);

        assertEquals(id, consultorioAActualizar.getId());
        assertEquals(consultorioMock, resultado);
        verify(service, times(1)).crear(consultorioAActualizar);
    }

    @Test
    void eliminarOk() {
        Long id = 1L;
        doNothing().when(service).eliminar(id);

        controller.eliminar(id);

        verify(service, times(1)).eliminar(id);
    }
}