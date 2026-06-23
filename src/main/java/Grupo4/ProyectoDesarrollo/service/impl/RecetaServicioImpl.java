package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.repository.RecetaRepository;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaServicioImpl implements RecetaServicio {

    private final RecetaRepository repository;

    @Override
    public List<Receta> findAll() {
        return repository.findAll();
    }

    @Override
    public Receta findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con id: " + id));
    }

    @Override
    public Receta save(Receta receta) {
        return repository.save(receta);
    }

    @Override
    public Receta update(Long id, Receta receta) {
        Receta existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con id: " + id));

        existente.setConsultaMedica(receta.getConsultaMedica());
        existente.setIndicaciones(receta.getIndicaciones());
        existente.setMedico(receta.getMedico());
        existente.setPaciente(receta.getPaciente());
        existente.setDetalles(receta.getDetalles());
        existente.setFechaEmision(receta.getFechaEmision());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Receta no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<Receta> buscarPorPacienteUsername(String username) {
        return repository.findByPacienteUsuarioUsername(username);
    }
}
