package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.Genero;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import Grupo4.ProyectoDesarrollo.repository.ConsultaMedicaRepository;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReporteServiceImplTest {

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConsultaMedicaRepository consultaMedicaRepository;

    @InjectMocks
    private ReporteServiceImpl service;

    @Test
    void testReporteIngresos() {
        when(facturaRepository.sumTotalFacturadoPorEstadoYFechas(eq(FacturaEstado.PAGADA), any(), any()))
                .thenReturn(BigDecimal.valueOf(1500.00), BigDecimal.valueOf(500.00));

        String result = service.reporteIngresos();

        assertNotNull(result);
        assertTrue(result.contains("Total Histórico: S/. 1500.00"));
        assertTrue(result.contains("Total de este mes: S/. 500.00"));
    }

    @Test
    void testReporteCitas() {
        when(citaRepository.countByEstado(any(CitaEstado.class))).thenReturn(5L);

        String result = service.reporteCitas();

        assertNotNull(result);
        assertTrue(result.contains("PENDIENTE: 5"));
        assertTrue(result.contains("COMPLETADA: 5"));
    }

    @Test
    void testReportePacientes() {
        Paciente p1 = new Paciente();
        p1.setGenero(Genero.MASCULINO);
        Paciente p2 = new Paciente();
        p2.setGenero(Genero.FEMENINO);
        Paciente p3 = new Paciente();
        p3.setGenero(Genero.MASCULINO);

        when(pacienteRepository.count()).thenReturn(3L);
        when(pacienteRepository.findAll()).thenReturn(List.of(p1, p2, p3));

        String result = service.reportePacientes();

        assertNotNull(result);
        assertTrue(result.contains("Total Registrados: 3"));
        assertTrue(result.contains("Masculinos: 2"));
        assertTrue(result.contains("Femeninos: 1"));
    }

    @Test
    void testReporteMedicos() {
        List<Object[]> rankingMock = new ArrayList<>();
        rankingMock.add(new Object[]{1L, "Gregory", "House", 12L});
        rankingMock.add(new Object[]{2L, "John", "Watson", 8L});

        when(consultaMedicaRepository.obtenerRankingMedicosPorConsultas()).thenReturn(rankingMock);

        String result = service.reporteMedicos();

        assertNotNull(result);
        assertTrue(result.contains("Dr. Gregory House"));
        assertTrue(result.contains("Consultas: 12"));
        assertTrue(result.contains("Dr. John Watson"));
        assertTrue(result.contains("Consultas: 8"));
    }

    @Test
    void testReporteMedicosVacio() {
        when(consultaMedicaRepository.obtenerRankingMedicosPorConsultas()).thenReturn(new ArrayList<>());

        String result = service.reporteMedicos();

        assertNotNull(result);
        assertEquals("No hay consultas médicas registradas para generar el ranking de médicos.", result);
    }
}