package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.repository.RecetaRepository;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaServicioImpl implements RecetaServicio {

    private final RecetaRepository repository;

    public RecetaServicioImpl(RecetaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Receta> findAll() {
        return repository.findAll();
    }

    @Override
    public Receta findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Receta save(Receta receta) {
        return repository.save(receta);
    }

    @Override
    public Receta update(Long id, Receta receta) {
        Receta existente = repository.findById(id).orElse(null);
        if (existente == null) return null;

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
        repository.deleteById(id);

    }
}
