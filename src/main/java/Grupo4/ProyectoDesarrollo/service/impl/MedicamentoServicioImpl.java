package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.repository.MedicamentoRepository;
import Grupo4.ProyectoDesarrollo.service.MedicamentoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoServicioImpl implements MedicamentoServicio {

    private final MedicamentoRepository repository;

    @Override
    public List<Medicamento> findAll() {
        return repository.findAll();
    }

    @Override
    public Medicamento findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con id: " + id));
    }

    @Override
    public Medicamento save(Medicamento medicamento) {
        return repository.save(medicamento);
    }

    @Override
    public Medicamento update(Long id, Medicamento medicamento) {
        Medicamento existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con id: " + id));

        existente.setNombreComercial(medicamento.getNombreComercial());
        existente.setNombreGenerico(medicamento.getNombreGenerico());
        existente.setPresentacion(medicamento.getPresentacion());
        existente.setConcentracion(medicamento.getConcentracion());
        existente.setViaAdministracion(medicamento.getViaAdministracion());
        existente.setActivo(medicamento.getActivo());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Medicamento no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
