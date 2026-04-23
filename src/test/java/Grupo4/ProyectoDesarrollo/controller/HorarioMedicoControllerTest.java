package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.service.HorarioMedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioMedicoControllerTest {

    @Mock
    private HorarioMedicoService service;

    @InjectMocks
    private HorarioMedicoController controller;

    private HorarioMedico horarioMock;

    @BeforeEach
    void setUp() {
        horarioMock = new HorarioMedico();
        horarioMock.setId(1L);
        horarioMock.setDiaSemana(DayOfWeek.MONDAY);
        horarioMock.setHoraInicio(LocalTime.of(8, 0));
        horarioMock.setHoraFin(LocalTime.of(12, 0));
        horarioMock.setDuracionTurnoMinutos(30);
        horarioMock.setActivo(true);
    }

    @Test
    void listarOk() {
        List<HorarioMedico> lista = Arrays.asList(horarioMock, new HorarioMedico());
        when(service.listar()).thenReturn(lista);

        List<HorarioMedico> response = controller.listar();

        assertEquals(2, response.size());
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorIdOk() {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(horarioMock);

        HorarioMedico response = controller.buscarPorId(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(service, times(1)).buscarPorId(id);
    }

    @Test
    void buscarPorIdNotFound() {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(null);

        HorarioMedico response = controller.buscarPorId(id);

        assertNull(response);
        verify(service, times(1)).buscarPorId(id);
    }

    @Test
    void crearOk() {
        when(service.crear(any(HorarioMedico.class))).thenReturn(horarioMock);

        HorarioMedico response = controller.crear(new HorarioMedico());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(service, times(1)).crear(any(HorarioMedico.class));
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        when(service.crear(any(HorarioMedico.class))).thenReturn(horarioMock);

        HorarioMedico response = controller.actualizar(id, new HorarioMedico());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(service, times(1)).crear(any(HorarioMedico.class));
    }

    @Test
    void eliminarOk() {
        Long id = 1L;
        doNothing().when(service).eliminar(id);

        controller.eliminar(id);

        verify(service, times(1)).eliminar(id);
    }
}