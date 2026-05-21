package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ConsultorioDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultorioControllerTest {

    @Mock
    private ConsultorioService service;
    @Mock
    private ClinicaService clinicaService;

    @InjectMocks
    private ConsultorioController controller;

    private Consultorio consultorioMock;
    private Clinica clinicaMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(2L);
        consultorioMock = new Consultorio();
        consultorioMock.setId(1L);
        consultorioMock.setClinica(clinicaMock);
    }

    @Test
    void crearOk() {
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(Consultorio.class))).thenReturn(consultorioMock);

        ConsultorioDTO dto = ConsultorioDTO.fromEntity(consultorioMock);
        ConsultorioDTO resultado = controller.crear(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(service, times(1)).crear(any(Consultorio.class));
    }

    @Test
    void listarOk() {
        List<Consultorio> listaEsperada = Arrays.asList(consultorioMock, consultorioMock);
        when(service.listar()).thenReturn(listaEsperada);

        List<ConsultorioDTO> resultado = controller.listar();

        assertEquals(2, resultado.size());
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorIdOk() {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(consultorioMock);

        ConsultorioDTO resultado = controller.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(service, times(1)).buscarPorId(id);
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        ConsultorioDTO dto = ConsultorioDTO.builder().clinicaId(2L).build();
        when(clinicaService.buscarPorId(2L)).thenReturn(clinicaMock);
        when(service.crear(any(Consultorio.class))).thenReturn(consultorioMock);

        ConsultorioDTO resultado = controller.actualizar(id, dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(service, times(1)).crear(any(Consultorio.class));
    }

    @Test
    void eliminarOk() {
        Long id = 1L;
        doNothing().when(service).eliminar(id);

        controller.eliminar(id);

        verify(service, times(1)).eliminar(id);
    }
}