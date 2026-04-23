package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.repository.ClinicaRepository;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicaServiceImpl implements ClinicaService {

    private final ClinicaRepository repository;

    @Override
    public Clinica crear(Clinica clinica) {
        return repository.save(clinica);
    }

    @Override
    public List<Clinica> listar() {
        return repository.findAll();
    }

    @Override
    public Clinica buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinica no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
