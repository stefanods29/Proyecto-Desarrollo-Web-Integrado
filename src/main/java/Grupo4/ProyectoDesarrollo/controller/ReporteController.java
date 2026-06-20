package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService service;

    @GetMapping("/ingresos")
    public String reporteIngresos() {
        return service.reporteIngresos();
    }

    @GetMapping("/citas")
    public String reporteCitas() {
        return service.reporteCitas();
    }

    @GetMapping("/pacientes")
    public String reportePacientes() {
        return service.reportePacientes();
    }

    @GetMapping("/medicos")
    public String reporteMedicos() {
        return service.reporteMedicos();
    }
}