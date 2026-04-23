package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.repository.DetalleRecetaRepository;
import Grupo4.ProyectoDesarrollo.service.DetalleRecetaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleRecetaServicioImpl implements DetalleRecetaServicio {

    private final DetalleRecetaRepository repository;

    @Override
    public List<DetalleReceta> findAll() {
        return repository.findAll();
    }

    @Override
    public DetalleReceta findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Detalle de receta con ID " + id + " no encontrado."));
    }

    @Override
    public DetalleReceta save(DetalleReceta detalle) {
        return repository.save(detalle);
    }

    @Override
    public DetalleReceta update(Long id, DetalleReceta detalle) {
        DetalleReceta existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: No se puede actualizar. El detalle con ID " + id + " no existe."));

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
        if (!repository.existsById(id)) {
            throw new RuntimeException("Error: No se puede eliminar. El detalle con ID " + id + " no existe.");
        }

        repository.deleteById(id);
    }
}