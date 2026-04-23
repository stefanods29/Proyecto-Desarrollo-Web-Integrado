package Grupo4.ProyectoDesarrollo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @GetMapping("/ingresos")
    public String reporteIngresos() {
        return "Reporte de ingresos";
    }

    @GetMapping("/citas")
    public String reporteCitas() {
        return "Reporte de citas";
    }

    @GetMapping("/pacientes")
    public String reportePacientes() {
        return "Reporte de pacientes";
    }

    @GetMapping("/medicos")
    public String reporteMedicos() {
        return "Reporte de medicos";
    }
}
