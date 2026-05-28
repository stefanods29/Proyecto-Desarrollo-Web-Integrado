package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.repository.EspecialidadRepository;
import Grupo4.ProyectoDesarrollo.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + id));
    }

    @Override
    public Especialidad actualizar(Long id, Especialidad especialidad) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Especialidad no encontrada con id: " + id);
        }
        especialidad.setId(id);
        return repository.save(especialidad);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Especialidad no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<Especialidad> buscarPorNombreIgnoreCase(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }
}