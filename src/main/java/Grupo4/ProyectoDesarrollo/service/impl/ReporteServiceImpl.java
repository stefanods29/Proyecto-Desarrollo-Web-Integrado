package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.service.ReporteService;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Override
    public String reporteIngresos() {
        return "Reporte de ingresos";
    }

    @Override
    public String reporteCitas() {
        return "Reporte de citas";
    }

    @Override
    public String reportePacientes() {
        return "Reporte de pacientes";
    }

    @Override
    public String reporteMedicos() {
        return "Reporte de medicos";
    }
}