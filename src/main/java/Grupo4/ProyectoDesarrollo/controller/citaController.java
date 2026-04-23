package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.CitaDTO;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private List<CitaDTO> citas = new ArrayList<>();
    @GetMapping
    public List<CitaDTO> listarCitas() {
        return citas;
    }


    @GetMapping("/{id}")
    public CitaDTO obtenerCita(@PathVariable Long id) {

        for (CitaDTO c : citas) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }


    @PostMapping
    public CitaDTO crearCita(@RequestBody CitaDTO cita) {
        cita.setId((long) (citas.size() + 1));
        citas.add(cita);
        return cita;
    }


    @PutMapping("/{id}")
    public CitaDTO actualizarCita(@PathVariable Long id, @RequestBody CitaDTO citaActualizada) {
        for (CitaDTO c : citas) {
            if (c.getId().equals(id)) {
                c.setPacienteId(citaActualizada.getPacienteId());
                c.setMedicoId(citaActualizada.getMedicoId());
                c.setFechaHora(citaActualizada.getFechaHora());
                c.setEstado(citaActualizada.getEstado());
                return c;
            }
        }
        return null;
    }


    @DeleteMapping("/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citas.removeIf(c -> c.getId().equals(id));
        return "Cita eliminada";
    }
}
