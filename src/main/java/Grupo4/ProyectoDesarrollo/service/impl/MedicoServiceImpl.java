package Grupo4.ProyectoDesarrollo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;



import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.repository.MedicoRepository;
import Grupo4.ProyectoDesarrollo.service.MedicoService;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    @Override
    public Medico crear(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    public List<Medico> listar() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        medicoRepository.deleteById(id);
    }

    @Override
    public Medico actualizar(Long id, Medico medico) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }
}