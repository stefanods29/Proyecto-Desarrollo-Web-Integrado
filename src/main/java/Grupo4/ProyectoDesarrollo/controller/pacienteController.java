package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.pacienteDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class pacienteController {
    private List<pacienteDTO> pacientes = new ArrayList<>();

    @GetMapping
    public List<pacienteDTO> listarPacientes() {
        return pacientes;
    }
    @GetMapping("/{id}")
    public pacienteDTO obtenerPaciente(@PathVariable Long id) {
        for (pacienteDTO p : pacientes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }


    @PostMapping
    public pacienteDTO crearPaciente(@RequestBody pacienteDTO paciente) {
        paciente.setId((long) (pacientes.size() + 1));
        pacientes.add(paciente);
        return paciente;
    }


    @PutMapping("/{id}")
    public pacienteDTO actualizarPaciente(
            @PathVariable Long id,
            @RequestBody pacienteDTO pacienteActualizado) {
        for (pacienteDTO p : pacientes) {
            if (p.getId().equals(id)) {
                p.setNombre(pacienteActualizado.getNombre());
                p.setDni(pacienteActualizado.getDni());
                p.setTelefono(pacienteActualizado.getTelefono());
                p.setCorreo(pacienteActualizado.getCorreo());
                return p;
            }
        }
        return null;
    }

    
    @DeleteMapping("/{id}")
    public String eliminarPaciente(@PathVariable Long id) {

        pacientes.removeIf(p -> p.getId().equals(id));

        return "Paciente eliminado";
    }
}
