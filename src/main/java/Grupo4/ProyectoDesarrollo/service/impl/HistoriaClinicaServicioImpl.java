package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.repository.HistoriaClinicaRepository;
import Grupo4.ProyectoDesarrollo.service.HistoriaClinicaServicio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriaClinicaServicioImpl implements HistoriaClinicaServicio {

    private final HistoriaClinicaRepository repository;

    public HistoriaClinicaServicioImpl(HistoriaClinicaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HistoriaClinica> findAll() {
        return repository.findAll();
    }

    @Override
    public HistoriaClinica findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public HistoriaClinica save(HistoriaClinica historia) {
        return repository.save(historia);
    }

    @Override
    public HistoriaClinica update(Long id, HistoriaClinica historia) {
        HistoriaClinica existente = repository.findById(id).orElse(null);
        if (existente == null) return null;

        existente.setPaciente(historia.getPaciente());
        existente.setClinica(historia.getClinica());
        existente.setFechaCreacion(historia.getFechaCreacion());
        existente.setConsultas(historia.getConsultas());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }
}
