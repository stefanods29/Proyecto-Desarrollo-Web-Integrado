package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Grupo4.ProyectoDesarrollo.service.ReporteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class ReporteControllerTest {

    @Mock
    private ReporteService service;

    @InjectMocks
    private ReporteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReporteIngresos() {
        when(service.reporteIngresos()).thenReturn("Reporte de ingresos");

        String result = controller.reporteIngresos();

        assertEquals("Reporte de ingresos", result);
    }

    @Test
    void testReporteCitas() {
        when(service.reporteCitas()).thenReturn("Reporte de citas");

        String result = controller.reporteCitas();

        assertEquals("Reporte de citas", result);
    }

    @Test
    void testReportePacientes() {
        when(service.reportePacientes()).thenReturn("Reporte de pacientes");

        String result = controller.reportePacientes();

        assertEquals("Reporte de pacientes", result);
    }

    @Test
    void testReporteMedicos() {
        when(service.reporteMedicos()).thenReturn("Reporte de medicos");

        String result = controller.reporteMedicos();

        assertEquals("Reporte de medicos", result);
    }
}