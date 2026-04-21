package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.service.HorarioMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioMedicoController {

    private final HorarioMedicoService service;

    @PostMapping
    public HorarioMedico crear(@RequestBody HorarioMedico horario) {
        return service.crear(horario);
    }

    @GetMapping
    public List<HorarioMedico> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public HorarioMedico buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public HorarioMedico actualizar(@PathVariable Long id,
                                     @RequestBody HorarioMedico horario) {
        horario.setId(id);
        return service.crear(horario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}