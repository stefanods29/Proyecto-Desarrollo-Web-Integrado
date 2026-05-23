package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.BusinessRuleException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import Grupo4.ProyectoDesarrollo.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository repository;

    @Override
    public Cita crear(Cita cita) {
        if (cita.getId() != null && repository.existsById(cita.getId())) {
            Cita existente = repository.findById(cita.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + cita.getId()));

            if (cita.getEstado() != existente.getEstado()) {
                validarTransicionEstado(existente.getEstado(), cita.getEstado());
            }
        } else if (cita.getEstado() == null) {
            cita.setEstado(CitaEstado.PENDIENTE);
        }
        validarCita(cita);
        return repository.save(cita);
    }

    @Override
    public List<Cita> listar() {
        return repository.findAll();
    }

    @Override
    public Cita buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        Cita existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
        repository.delete(existente);
    }

    @Override
    public List<Cita> buscarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Cita> buscarPorMedico(Long medicoId) {
        return repository.findByMedicoId(medicoId);
    }

    @Override
    public Cita cambiarEstado(Long id, CitaEstado nuevoEstado) {
        Cita cita = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
        
        validarTransicionEstado(cita.getEstado(), nuevoEstado);
        
        cita.setEstado(nuevoEstado);
        return repository.save(cita);
    }

    private void validarCita(Cita cita) {
        if (cita.getFechaHora() == null || cita.getFechaFin() == null) {
            throw new BusinessRuleException("La fecha/hora de inicio y de fin son obligatorias");
        }
        if (!cita.getFechaFin().isAfter(cita.getFechaHora())) {
            throw new BusinessRuleException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        if (cita.getMedico() == null || cita.getMedico().getId() == null) {
            throw new BusinessRuleException("El médico es obligatorio");
        }
        if (cita.getConsultorio() == null || cita.getConsultorio().getId() == null) {
            throw new BusinessRuleException("El consultorio es obligatorio");
        }

        boolean medicoTraslapado = repository.existsOverlappingCitaForMedico(
                cita.getMedico().getId(), 
                cita.getFechaHora(), 
                cita.getFechaFin(), 
                cita.getId()
        );
        if (medicoTraslapado) {
            throw new BusinessRuleException("El médico tiene otra cita programada que se solapa con este horario");
        }

        boolean consultorioTraslapado = repository.existsOverlappingCitaForConsultorio(
                cita.getConsultorio().getId(), 
                cita.getFechaHora(), 
                cita.getFechaFin(), 
                cita.getId()
        );
        if (consultorioTraslapado) {
            throw new BusinessRuleException("El consultorio ya está ocupado en este horario");
        }
    }

    private void validarTransicionEstado(CitaEstado estadoActual, CitaEstado nuevoEstado) {
        if (estadoActual == nuevoEstado) {
            return;
        }

        if (nuevoEstado == CitaEstado.CANCELADA && estadoActual == CitaEstado.COMPLETADA) {
            throw new BusinessRuleException("No se puede cancelar una cita que ya ha sido completada");
        }

        boolean esTransicionValida = false;
        switch (estadoActual) {
            case PENDIENTE:
                esTransicionValida = (nuevoEstado == CitaEstado.CONFIRMADA || nuevoEstado == CitaEstado.CANCELADA);
                break;
            case CONFIRMADA:
                esTransicionValida = (nuevoEstado == CitaEstado.EN_ATENCION || nuevoEstado == CitaEstado.CANCELADA);
                break;
            case EN_ATENCION:
                esTransicionValida = (nuevoEstado == CitaEstado.COMPLETADA || nuevoEstado == CitaEstado.CANCELADA);
                break;
            case COMPLETADA:
            case CANCELADA:
            case NO_ASISTIO:
                esTransicionValida = false;
                break;
        }

        if (!esTransicionValida) {
            throw new BusinessRuleException("Transición de estado no permitida de " + estadoActual + " a " + nuevoEstado);
        }
    }
}
