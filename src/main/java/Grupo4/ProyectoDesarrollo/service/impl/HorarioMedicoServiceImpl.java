package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.repository.HorarioMedicoRepository;
import Grupo4.ProyectoDesarrollo.service.HorarioMedicoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioMedicoServiceImpl implements HorarioMedicoService {

    private final HorarioMedicoRepository repository;

    @Override
    public HorarioMedico crear(HorarioMedico horario) {
        return repository.save(horario);
    }

    @Override
    public List<HorarioMedico> listar() {
        return repository.findAll();
    }

    @Override
    public HorarioMedico buscarPorId(Long id) {
        return repository.findById(id)
                .orElse(null); // puedes cambiarlo por exception si quieres
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}