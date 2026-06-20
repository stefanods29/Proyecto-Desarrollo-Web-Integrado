package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.repository.HorarioMedicoRepository;
import Grupo4.ProyectoDesarrollo.service.HorarioMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioMedicoServiceImpl implements HorarioMedicoService {

    private final HorarioMedicoRepository horarioMedicoRepository;

    @Override
    public HorarioMedico crear(HorarioMedico horario) {
        return horarioMedicoRepository.save(horario);
    }

    @Override
    public List<HorarioMedico> listar() {
        return horarioMedicoRepository.findAll();
    }

    @Override
    public HorarioMedico buscarPorId(Long id) {
        return horarioMedicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario médico no encontrado con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        if (!horarioMedicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Horario médico no encontrado con id: " + id);
        }
        horarioMedicoRepository.deleteById(id);
    }
}
