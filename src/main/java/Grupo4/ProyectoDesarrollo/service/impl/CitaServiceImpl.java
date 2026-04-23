package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import Grupo4.ProyectoDesarrollo.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository repository;

    @Override
    public Cita crear(Cita cita) {
        return repository.save(cita);
    }

    @Override
    public List<Cita> listar() {
        return repository.findAll();
    }

    @Override
    public Cita buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
