package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ConsultaMedicaDTO;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.service.*;
import Grupo4.ProyectoDesarrollo.service.impl.HistoriaClinicaServicioImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaMedicaControllerTest {
    @Mock
    private ConsultaMedicaServicio servicio;
    @Mock
    private HistoriaClinicaServicioImpl historiaClinicaService;
    @Mock
    private PacienteService pacienteService;
    @Mock
    private MedicoService medicoService;
    @Mock
    private ClinicaService clinicaService;
    @Mock
    private CitaService citaService;

    @InjectMocks
    private ConsultaMedicaController controller;

    private ConsultaMedica consultaMock;
    private HistoriaClinica historiaMock;
    private Paciente pacienteMock;
    private Medico medicoMock;
    private Clinica clinicaMock;
    private Cita citaMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(10L);
        pacienteMock = new Paciente();
        pacienteMock.setId(2L);
        medicoMock = new Medico();
        medicoMock.setId(3L);
        historiaMock = new HistoriaClinica();
        historiaMock.setId(4L);
        citaMock = new Cita();
        citaMock.setId(5L);

        consultaMock = new ConsultaMedica();
        consultaMock.setId(1L);
        consultaMock.setHistoriaClinica(historiaMock);
        consultaMock.setPaciente(pacienteMock);
        consultaMock.setMedico(medicoMock);
        consultaMock.setClinica(clinicaMock);
        consultaMock.setCita(citaMock);
    }

    @Test
    void listarOk() {
        List<ConsultaMedica> listaConsultas = Arrays.asList(consultaMock, consultaMock);
        when(servicio.findAll()).thenReturn(listaConsultas);

        ResponseEntity<List<ConsultaMedicaDTO>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void obtenerOk() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(consultaMock);

        ResponseEntity<ConsultaMedicaDTO> response = controller.obtener(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void obtenerok() {
        Long id = 1L;
        when(servicio.findById(id)).thenThrow(new ResourceNotFoundException("Consulta médica no encontrada con id: 1"));

        assertThrows(ResourceNotFoundException.class, () -> controller.obtener(id));
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void crearOk() {
        when(historiaClinicaService.buscarPorId(any())).thenReturn(historiaMock);
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(citaService.buscarPorId(any())).thenReturn(citaMock);
        when(servicio.save(any(ConsultaMedica.class))).thenReturn(consultaMock);

        ConsultaMedicaDTO dto = ConsultaMedicaDTO.fromEntity(consultaMock);
        ResponseEntity<ConsultaMedicaDTO> response = controller.crear(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).save(any(ConsultaMedica.class));
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        when(historiaClinicaService.buscarPorId(any())).thenReturn(historiaMock);
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(citaService.buscarPorId(any())).thenReturn(citaMock);
        when(servicio.update(eq(id), any(ConsultaMedica.class))).thenReturn(consultaMock);

        ConsultaMedicaDTO dto = ConsultaMedicaDTO.fromEntity(consultaMock);
        ResponseEntity<ConsultaMedicaDTO> response = controller.actualizar(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).update(eq(id), any(ConsultaMedica.class));
    }

    @Test
    void actualizarok() {
        Long id = 1L;
        when(historiaClinicaService.buscarPorId(any())).thenReturn(historiaMock);
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(citaService.buscarPorId(any())).thenReturn(citaMock);
        when(servicio.update(eq(id), any(ConsultaMedica.class)))
                .thenThrow(new ResourceNotFoundException("Consulta médica no encontrada con id: 1"));

        ConsultaMedicaDTO dto = ConsultaMedicaDTO.fromEntity(consultaMock);
        assertThrows(ResourceNotFoundException.class, () -> controller.actualizar(id, dto));
        verify(servicio, times(1)).update(eq(id), any(ConsultaMedica.class));
    }

    @Test
    void eliminarOk() {
        Long id = 1L;

        doNothing().when(servicio).delete(id);

        ResponseEntity<Void> response = controller.eliminar(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(servicio, times(1)).delete(id);
    }
}