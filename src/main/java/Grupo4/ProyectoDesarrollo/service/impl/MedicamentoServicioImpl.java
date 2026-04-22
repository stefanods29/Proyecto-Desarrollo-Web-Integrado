package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.repository.MedicamentoRepository;
import Grupo4.ProyectoDesarrollo.service.MedicamentoServicio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoServicioImpl implements MedicamentoServicio {
    private final MedicamentoRepository repository;

    public MedicamentoServicioImpl(MedicamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Medicamento> findAll() {
        return repository.findAll();
    }

    @Override
    public Medicamento findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Medicamento save(Medicamento medicamento) {
        return repository.save(medicamento);
    }

    @Override
    public Medicamento update(Long id, Medicamento medicamento) {
        Medicamento existente = repository.findById(id).orElse(null);
        if (existente == null) return null;
        existente.setNombreComercial(medicamento.getNombreComercial());
        existente.setNombreGenerico(medicamento.getNombreGenerico());
        existente.setPresentacion(medicamento.getPresentacion());
        existente.setConcentracion(medicamento.getConcentracion());
        existente.setViaAdministracion(medicamento.getViaAdministracion());
        existente.setActivo(medicamento.isActivo());
        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id
        );

    }
}
