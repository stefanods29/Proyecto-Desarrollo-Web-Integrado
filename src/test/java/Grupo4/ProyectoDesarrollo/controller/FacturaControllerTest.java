package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.FacturaDTO;
import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.service.CitaService;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.FacturaService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaControllerTest {

    @Mock
    private FacturaService service;
    @Mock
    private PacienteService pacienteService;
    @Mock
    private CitaService citaService;
    @Mock
    private ClinicaService clinicaService;

    @InjectMocks
    private FacturaController controller;

    private Factura facturaMock;
    private Paciente pacienteMock;
    private Cita citaMock;
    private Clinica clinicaMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(10L);
        pacienteMock = Paciente.builder().id(2L).build();
        citaMock = Cita.builder().id(3L).build();

        facturaMock = new Factura();
        facturaMock.setId(1L);
        facturaMock.setNumeroFactura("F001");
        facturaMock.setPaciente(pacienteMock);
        facturaMock.setCita(citaMock);
        facturaMock.setClinica(clinicaMock);
    }

    @Test
    void crearOk() {
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(citaService.buscarPorId(any())).thenReturn(citaMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(Factura.class))).thenReturn(facturaMock);

        FacturaDTO dto = FacturaDTO.fromEntity(facturaMock);
        ResponseEntity<FacturaDTO> response = controller.crear(dto);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("F001", response.getBody().getNumeroFactura());
        verify(service, times(1)).crear(any(Factura.class));
    }

    @Test
    void listarOk() {
        List<Factura> lista = Arrays.asList(facturaMock, facturaMock);
        when(service.listar()).thenReturn(lista);

        ResponseEntity<List<FacturaDTO>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorIdOk() {
        when(service.buscarPorId(1L)).thenReturn(facturaMock);

        ResponseEntity<FacturaDTO> response = controller.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorIdNotFound() {
        when(service.buscarPorId(99L)).thenThrow(new Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException("Factura no encontrada con id: 99"));

        assertThrows(Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException.class, () -> controller.buscarPorId(99L));
        verify(service, times(1)).buscarPorId(99L);
    }

    @Test
    void actualizarOk() {
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(citaService.buscarPorId(any())).thenReturn(citaMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(Factura.class))).thenReturn(facturaMock);

        FacturaDTO dto = FacturaDTO.fromEntity(facturaMock);
        ResponseEntity<FacturaDTO> response = controller.actualizar(1L, dto);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).crear(any(Factura.class));
    }
}