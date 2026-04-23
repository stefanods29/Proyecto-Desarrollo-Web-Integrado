package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.service.EspecialidadService;
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
class EspecialidadControllerTest {

    @Mock
    private EspecialidadService service;

    @InjectMocks
    private EspecialidadController controller;

    private Especialidad especialidadMock;

    @BeforeEach
    void setUp() {
        especialidadMock = new Especialidad();
        especialidadMock.setId(1L);
    }

    @Test
    void crear_ok() {
        when(service.crear(any(Especialidad.class))).thenReturn(especialidadMock);

        Especialidad resultado = controller.crear(new Especialidad());

        assertEquals(especialidadMock, resultado);
        verify(service, times(1)).crear(any(Especialidad.class));
    }

    @Test
    void listar_ok() {
        List<Especialidad> listaEsperada = Arrays.asList(especialidadMock, new Especialidad());
        when(service.listar()).thenReturn(listaEsperada);

        List<Especialidad> resultado = controller.listar();

        assertEquals(2, resultado.size());
        assertEquals(listaEsperada, resultado);
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorId_ok() {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(especialidadMock);

        Especialidad resultado = controller.buscarPorId(id);

        assertEquals(especialidadMock, resultado);
        verify(service, times(1)).buscarPorId(id);
    }

    @Test
    void actualizar_ok() {
        Long id = 2L;
        Especialidad especialidadAActualizar = new Especialidad();
        when(service.crear(any(Especialidad.class))).thenReturn(especialidadMock);

        Especialidad resultado = controller.actualizar(id, especialidadAActualizar);

        assertEquals(id, especialidadAActualizar.getId());
        assertEquals(especialidadMock, resultado);
        verify(service, times(1)).crear(especialidadAActualizar);
    }

    @Test
    void eliminar_ok() {
        Long id = 1L;
        doNothing().when(service).eliminar(id);

        controller.eliminar(id);

        verify(service, times(1)).eliminar(id);
    }
}