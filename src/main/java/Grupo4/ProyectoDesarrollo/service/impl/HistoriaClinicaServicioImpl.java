package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.repository.HistoriaClinicaRepository;
import Grupo4.ProyectoDesarrollo.service.HistoriaClinicaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriaClinicaServicioImpl implements HistoriaClinicaServicio {

    private final HistoriaClinicaRepository repository;

    @Override
    public List<HistoriaClinica> findAll() {
        return repository.findAll();
    }

    @Override
    public HistoriaClinica buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada con id: " + id));
    }

    @Override
    public HistoriaClinica save(HistoriaClinica historia) {
        return repository.save(historia);
    }

    @Override
    public HistoriaClinica update(Long id, HistoriaClinica historia) {
        HistoriaClinica existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada con id: " + id));

        existente.setPaciente(historia.getPaciente());
        existente.setClinica(historia.getClinica());
        existente.setFechaCreacion(historia.getFechaCreacion());
        existente.setConsultas(historia.getConsultas());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Historia clínica no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}
