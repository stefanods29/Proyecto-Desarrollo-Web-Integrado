package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.repository.HorarioMedicoRepository;
import Grupo4.ProyectoDesarrollo.service.HorarioMedicoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioMedicoServiceImpl implements HorarioMedicoService {

    private final HorarioMedicoRepository repository;


    public HorarioMedicoServiceImpl(HorarioMedicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public HorarioMedico crear(HorarioMedico horarioMedico) {
        return repository.save(horarioMedico);
    }
    @Override
    public List<HorarioMedico> listar() {
        return repository.findAll();
    }

    @Override
    public HorarioMedico buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public  void eliminar(Long id){
        repository.deleteById(id);
    }
}
