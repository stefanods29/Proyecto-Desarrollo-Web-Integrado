package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.CitaDTO;
import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import Grupo4.ProyectoDesarrollo.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaControllerTest {

    @Mock
    private CitaService service;
    @Mock
    private PacienteService pacienteService;
    @Mock
    private MedicoService medicoService;
    @Mock
    private ConsultorioService consultorioService;
    @Mock
    private ClinicaService clinicaService;

    @InjectMocks
    private CitaController controller;

    private Paciente pacienteMock;
    private Medico medicoMock;
    private Consultorio consultorioMock;
    private Clinica clinicaMock;

    @BeforeEach
    void setUp() {
        pacienteMock = Paciente.builder().id(1L).build();
        medicoMock = Medico.builder().id(10L).build();
        consultorioMock = Consultorio.builder().id(5L).build();
        clinicaMock = Clinica.builder().id(2L).build();
    }

    @Test
    void testListarCitas() {
        when(service.listar()).thenReturn(Collections.emptyList());
        List<CitaDTO> resultado = controller.listarCitas();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(service, times(1)).listar();
    }

    @Test
    void testListarCitasConDatos() {
        Cita cita1 = Cita.builder()
                .id(1L)
                .paciente(pacienteMock)
                .medico(medicoMock)
                .estado(CitaEstado.PENDIENTE)
                .build();
        Cita cita2 = Cita.builder()
                .id(2L)
                .paciente(pacienteMock)
                .medico(medicoMock)
                .estado(CitaEstado.CONFIRMADA)
                .build();

        when(service.listar()).thenReturn(Arrays.asList(cita1, cita2));

        List<CitaDTO> resultado = controller.listarCitas();
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(CitaEstado.PENDIENTE, resultado.get(0).getEstado());
        assertEquals(2L, resultado.get(1).getId());
        assertEquals(CitaEstado.CONFIRMADA, resultado.get(1).getEstado());
    }

    @Test
    void testObtenerCita() {
        Cita cita = Cita.builder()
                .id(1L)
                .paciente(pacienteMock)
                .medico(medicoMock)
                .estado(CitaEstado.PENDIENTE)
                .build();

        when(service.buscarPorId(1L)).thenReturn(cita);

        CitaDTO resultado = controller.obtenerCita(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(CitaEstado.PENDIENTE, resultado.getEstado());
    }

    @Test
    void testObtenerCitaidInexistente() {
        when(service.buscarPorId(99L)).thenReturn(null);
        CitaDTO resultado = controller.obtenerCita(99L);
        assertNull(resultado);
    }

    @Test
    void testCrearCita() {
        CitaDTO citaDTO = CitaDTO.builder()
                .pacienteId(1L)
                .medicoId(10L)
                .consultorioId(5L)
                .clinicaId(2L)
                .estado(CitaEstado.PENDIENTE)
                .build();

        Cita citaGuardada = Cita.builder()
                .id(1L)
                .paciente(pacienteMock)
                .medico(medicoMock)
                .consultorio(consultorioMock)
                .clinica(clinicaMock)
                .estado(CitaEstado.PENDIENTE)
                .build();

        when(pacienteService.buscarPorId(1L)).thenReturn(pacienteMock);
        when(medicoService.buscarPorId(10L)).thenReturn(medicoMock);
        when(consultorioService.buscarPorId(5L)).thenReturn(consultorioMock);
        when(clinicaService.buscarPorId(2L)).thenReturn(clinicaMock);
        when(service.crear(any(Cita.class))).thenReturn(citaGuardada);

        CitaDTO resultado = controller.crearCita(citaDTO);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(CitaEstado.PENDIENTE, resultado.getEstado());
        assertEquals(1L, resultado.getPacienteId());
        assertEquals(10L, resultado.getMedicoId());
    }

    @Test
    void testActualizarCita() {
        CitaDTO citaDTO = CitaDTO.builder()
                .pacienteId(1L)
                .medicoId(10L)
                .consultorioId(5L)
                .clinicaId(2L)
                .estado(CitaEstado.CONFIRMADA)
                .build();

        Cita citaGuardada = Cita.builder()
                .id(1L)
                .paciente(pacienteMock)
                .medico(medicoMock)
                .consultorio(consultorioMock)
                .clinica(clinicaMock)
                .estado(CitaEstado.CONFIRMADA)
                .build();

        when(pacienteService.buscarPorId(1L)).thenReturn(pacienteMock);
        when(medicoService.buscarPorId(10L)).thenReturn(medicoMock);
        when(consultorioService.buscarPorId(5L)).thenReturn(consultorioMock);
        when(clinicaService.buscarPorId(2L)).thenReturn(clinicaMock);
        when(service.crear(any(Cita.class))).thenReturn(citaGuardada);

        CitaDTO resultado = controller.actualizarCita(1L, citaDTO);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(CitaEstado.CONFIRMADA, resultado.getEstado());
    }

    @Test
    void testEliminarCita() {
        doNothing().when(service).eliminar(1L);
        String mensaje = controller.eliminarCita(1L);
        assertEquals("Cita eliminada", mensaje);
        verify(service, times(1)).eliminar(1L);
    }
}
