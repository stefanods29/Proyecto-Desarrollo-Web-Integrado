package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import Grupo4.ProyectoDesarrollo.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository repository;

    @Override
    public Factura crear(Factura factura) {
        return repository.save(factura);
    }

    @Override
    public List<Factura> listar() {
        return repository.findAll();
    }

    @Override
    public Factura buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
