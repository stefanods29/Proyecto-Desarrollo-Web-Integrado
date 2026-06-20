package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.HistoriaClinicaDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.HistoriaClinicaServicio;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoriaClinicaControllerTest {

    @Mock
    private HistoriaClinicaServicio servicio;
    @Mock
    private PacienteService pacienteService;
    @Mock
    private ClinicaService clinicaService;

    @InjectMocks
    private HistoriaClinicaController controller;

    private HistoriaClinica historiaMock;
    private Paciente pacienteMock;
    private Clinica clinicaMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(10L);
        pacienteMock = new Paciente();
        pacienteMock.setId(2L);

        historiaMock = new HistoriaClinica();
        historiaMock.setId(1L);
        historiaMock.setFechaCreacion(LocalDateTime.now());
        historiaMock.setPaciente(pacienteMock);
        historiaMock.setClinica(clinicaMock);
    }

    @Test
    void listarOk() {
        List<HistoriaClinica> lista = Arrays.asList(historiaMock, historiaMock);
        when(servicio.findAll()).thenReturn(lista);

        ResponseEntity<List<HistoriaClinicaDTO>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void obtenerOk() {
        when(servicio.buscarPorId(1L)).thenReturn(historiaMock);

        ResponseEntity<HistoriaClinicaDTO> response = controller.obtener(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).buscarPorId(1L);
    }

    @Test
    void obtenerNotFound() {
        when(servicio.buscarPorId(1L)).thenReturn(null);

        ResponseEntity<HistoriaClinicaDTO> response = controller.obtener(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).buscarPorId(1L);
    }

    @Test
    void crearOk() {
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(servicio.save(any(HistoriaClinica.class))).thenReturn(historiaMock);

        HistoriaClinicaDTO dto = HistoriaClinicaDTO.fromEntity(historiaMock);
        ResponseEntity<HistoriaClinicaDTO> response = controller.crear(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).save(any(HistoriaClinica.class));
    }

    @Test
    void actualizarOk() {
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(servicio.update(eq(1L), any(HistoriaClinica.class))).thenReturn(historiaMock);

        HistoriaClinicaDTO dto = HistoriaClinicaDTO.fromEntity(historiaMock);
        ResponseEntity<HistoriaClinicaDTO> response = controller.actualizar(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).update(eq(1L), any(HistoriaClinica.class));
    }

    @Test
    void actualizarNotFound() {
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(servicio.update(eq(1L), any(HistoriaClinica.class))).thenReturn(null);

        HistoriaClinicaDTO dto = HistoriaClinicaDTO.fromEntity(historiaMock);
        ResponseEntity<HistoriaClinicaDTO> response = controller.actualizar(1L, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).update(eq(1L), any(HistoriaClinica.class));
    }

    @Test
    void eliminarOk() {
        doNothing().when(servicio).delete(1L);

        ResponseEntity<String> response = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Historia clínica eliminada", response.getBody());
        verify(servicio, times(1)).delete(1L);
    }
}
