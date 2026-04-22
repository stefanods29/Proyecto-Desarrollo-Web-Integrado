package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.repository.DetalleRecetaRepository;
import Grupo4.ProyectoDesarrollo.service.DetalleRecetaServicio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleRecetaServicioImpl implements DetalleRecetaServicio {

    private final DetalleRecetaRepository repository;

    public DetalleRecetaServicioImpl(DetalleRecetaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DetalleReceta> findAll() {
        return repository.findAll();
    }

    @Override
    public DetalleReceta findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DetalleReceta save(DetalleReceta detalle) {
        return repository.save(detalle);
    }

    @Override
    public DetalleReceta update(Long id, DetalleReceta detalle) {
        DetalleReceta existente = repository.findById(id).orElse(null);
        if (existente == null) return null;

        existente.setReceta(detalle.getReceta());
        existente.setDuracion(detalle.getDuracion());
        existente.setDosis(detalle.getDosis());
        existente.setInstrucciones(detalle.getInstrucciones());
        existente.setFrecuencia(detalle.getFrecuencia());
        existente.setMedicamento(detalle.getMedicamento());

        return repository.save(existente);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
