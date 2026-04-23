package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.repository.EspecialidadRepository;
import Grupo4.ProyectoDesarrollo.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository repository;

    @Override
    public Especialidad crear(Especialidad especialidad) {
        return repository.save(especialidad);
    }

    @Override
    public List<Especialidad> listar() {
        return repository.findAll();
    }

    @Override
    public Especialidad buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Especialidad con ID " + id + " no encontrada."));
    }

    @Override
    public Especialidad actualizar(Long id, Especialidad especialidad) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Error: No se puede actualizar. La especialidad con ID " + id + " no existe.");
        }
        especialidad.setId(id);
        return repository.save(especialidad);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Error: No se puede eliminar. La especialidad con ID " + id + " no existe.");
        }
        repository.deleteById(id);
    }
}