package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.medicoDTO;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class medicoController {
    private List<medicoDTO> medicos = new ArrayList<>();

    @GetMapping
    public List<medicoDTO> listarMedicos() {
        return medicos;
    }


    @GetMapping("/{id}")
    public medicoDTO obtenerMedico(@PathVariable Long id) {
        for (medicoDTO m : medicos) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }


    @PostMapping
    public medicoDTO crearMedico(@RequestBody medicoDTO medico) {
        medico.setId((long) (medicos.size() + 1));
        medicos.add(medico);
        return medico;
    }


    @PutMapping("/{id}")
    public medicoDTO actualizarMedico(@PathVariable Long id,@RequestBody medicoDTO medicoActualizado) {
        for (medicoDTO m : medicos) {
            if (m.getId().equals(id)) {
                m.setNombre(medicoActualizado.getNombre());
                m.setEspecialidad(medicoActualizado.getEspecialidad());
                m.setTelefono(medicoActualizado.getTelefono());
                m.setCorreo(medicoActualizado.getCorreo());
                return m;
            }
        }
        return null;
    }


    @DeleteMapping("/{id}")
    public String eliminarMedico(@PathVariable Long id) {
        medicos.removeIf(m -> m.getId().equals(id));
        return "Medico eliminado";
    }
}
