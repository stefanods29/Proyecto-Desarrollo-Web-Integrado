package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.repository.ConsultaMedicaRepository;
import Grupo4.ProyectoDesarrollo.service.ConsultaMedicaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaMedicaServicioImpl implements ConsultaMedicaServicio {

    private final ConsultaMedicaRepository repository;

    @Override
    public List<ConsultaMedica> findAll() {
        return repository.findAll();
    }

    @Override
    public ConsultaMedica findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta médica no encontrada con id: " + id));
    }

    @Override
    public ConsultaMedica save(ConsultaMedica consulta) {
        return repository.save(consulta);
    }

    @Override
    public ConsultaMedica update(Long id, ConsultaMedica consulta) {
        ConsultaMedica existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta médica no encontrada con id: " + id));

        existente.setHistoriaClinica(consulta.getHistoriaClinica());
        existente.setMedico(consulta.getMedico());
        existente.setCita(consulta.getCita());
        existente.setAnamnesis(consulta.getAnamnesis());
        existente.setExamenFisico(consulta.getExamenFisico());
        existente.setDiagnostico(consulta.getDiagnostico());
        existente.setTratamiento(consulta.getTratamiento());
        existente.setObservaciones(consulta.getObservaciones());
        existente.setPresionArterial(consulta.getPresionArterial());
        existente.setTemperatura(consulta.getTemperatura());
        existente.setFrecuenciaCardiaca(consulta.getFrecuenciaCardiaca());
        existente.setFrecuenciaRespiratoria(consulta.getFrecuenciaRespiratoria());
        existente.setPeso(consulta.getPeso());
        existente.setTalla(consulta.getTalla());
        existente.setFechaConsulta(consulta.getFechaConsulta());
        existente.setFechaActualizacion(consulta.getFechaActualizacion());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Consulta médica no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}
