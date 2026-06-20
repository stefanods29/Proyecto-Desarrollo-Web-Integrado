package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.repository.ConsultorioRepository;
import Grupo4.ProyectoDesarrollo.service.ConsultorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultorioServiceImpl implements ConsultorioService {

    private final ConsultorioRepository repository;

    @Override
    public Consultorio crear(Consultorio consultorio) {
        return repository.save(consultorio);
    }

    @Override
    public List<Consultorio> listar() {
        return repository.findAll();
    }

    @Override
    public Consultorio buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + id));
    }

    @Override
    public Consultorio actualizar(Long id, Consultorio consultorio) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Consultorio no encontrado con id: " + id);
        }

        consultorio.setId(id);
        return repository.save(consultorio);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Consultorio no encontrado con id: " + id);
        }

        repository.deleteById(id);
    }
}