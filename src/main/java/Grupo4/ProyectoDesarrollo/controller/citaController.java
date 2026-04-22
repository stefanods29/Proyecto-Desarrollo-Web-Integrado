package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.citaDTO;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class citaController {
    private List<citaDTO> citas = new ArrayList<>();
    @GetMapping
    public List<citaDTO> listarCitas() {
        return citas;
    }


    @GetMapping("/{id}")
    public citaDTO obtenerCita(@PathVariable Long id) {

        for (citaDTO c : citas) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }


    @PostMapping
    public citaDTO crearCita(@RequestBody citaDTO cita) {
        cita.setId((long) (citas.size() + 1));
        citas.add(cita);
        return cita;
    }


    @PutMapping("/{id}")
    public citaDTO actualizarCita(@PathVariable Long id, @RequestBody citaDTO citaActualizada) {
        for (citaDTO c : citas) {
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
