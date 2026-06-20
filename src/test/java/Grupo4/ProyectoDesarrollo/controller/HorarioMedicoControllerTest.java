package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.HorarioMedicoDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.HorarioMedicoService;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
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
    @Mock
    private MedicoService medicoService;
    @Mock
    private ClinicaService clinicaService;

    @InjectMocks
    private HorarioMedicoController controller;

    private HorarioMedico horarioMock;
    private Medico medicoMock;
    private Clinica clinicaMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(2L);
        medicoMock = new Medico();
        medicoMock.setId(3L);
        medicoMock.setClinica(clinicaMock);

        horarioMock = new HorarioMedico();
        horarioMock.setId(1L);
        horarioMock.setDiaSemana(DayOfWeek.MONDAY);
        horarioMock.setHoraInicio(LocalTime.of(8, 0));
        horarioMock.setHoraFin(LocalTime.of(12, 0));
        horarioMock.setDuracionTurnoMinutos(30);
        horarioMock.setActivo(true);
        horarioMock.setMedico(medicoMock);
        horarioMock.setClinica(clinicaMock);
    }

    @Test
    void listarOk() {
        List<HorarioMedico> lista = Arrays.asList(horarioMock, horarioMock);
        when(service.listar()).thenReturn(lista);

        List<HorarioMedicoDTO> response = controller.listar();

        assertEquals(2, response.size());
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorIdOk() {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(horarioMock);

        HorarioMedicoDTO response = controller.buscarPorId(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(service, times(1)).buscarPorId(id);
    }

    @Test
    void buscarPorIdNotFound() {
        Long id = 1L;
        when(service.buscarPorId(id)).thenReturn(null);

        HorarioMedicoDTO response = controller.buscarPorId(id);

        assertNull(response);
        verify(service, times(1)).buscarPorId(id);
    }

    @Test
    void crearOk() {
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(HorarioMedico.class))).thenReturn(horarioMock);

        HorarioMedicoDTO dto = HorarioMedicoDTO.fromEntity(horarioMock);
        HorarioMedicoDTO response = controller.crear(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(service, times(1)).crear(any(HorarioMedico.class));
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(HorarioMedico.class))).thenReturn(horarioMock);

        HorarioMedicoDTO dto = HorarioMedicoDTO.fromEntity(horarioMock);
        HorarioMedicoDTO response = controller.actualizar(id, dto);

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