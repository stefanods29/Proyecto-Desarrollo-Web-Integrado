package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReporteServiceImplTest {

    private final ReporteServiceImpl service = new ReporteServiceImpl();

    @Test
    void testReporteIngresos() {
        assertEquals("Reporte de ingresos", service.reporteIngresos());
    }

    @Test
    void testReporteCitas() {
        assertEquals("Reporte de citas", service.reporteCitas());
    }

    @Test
    void testReportePacientes() {
        assertEquals("Reporte de pacientes", service.reportePacientes());
    }

    @Test
    void testReporteMedicos() {
        assertEquals("Reporte de medicos", service.reporteMedicos());
    }
}