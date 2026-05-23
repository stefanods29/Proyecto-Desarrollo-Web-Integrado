package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.DuplicateResourceException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;

    @Override
    public Paciente crear(Paciente paciente) {
        if (paciente.getId() != null) {
            Paciente existente = repository.findById(paciente.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + paciente.getId()));
            if (!existente.getNumeroDocumento().equalsIgnoreCase(paciente.getNumeroDocumento()) &&
                    repository.existsByNumeroDocumento(paciente.getNumeroDocumento())) {
                throw new DuplicateResourceException("El documento de identidad '" + paciente.getNumeroDocumento() + "' ya está registrado");
            }
        } else {
            if (repository.existsByNumeroDocumento(paciente.getNumeroDocumento())) {
                throw new DuplicateResourceException("El documento de identidad '" + paciente.getNumeroDocumento() + "' ya está registrado");
            }
        }
        return repository.save(paciente);
    }

    @Override
    public List<Paciente> listar() {
        return repository.findAll();
    }

    @Override
    public Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        Paciente existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
        repository.delete(existente);
    }

    @Override
    public Paciente buscarPorNumeroDocumento(String numeroDocumento) {
        return repository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con documento: " + numeroDocumento));
    }

    @Override
    public List<Paciente> buscarPorClinica(Long clinicaId) {
        return repository.findByClinicaId(clinicaId);
    }
}
