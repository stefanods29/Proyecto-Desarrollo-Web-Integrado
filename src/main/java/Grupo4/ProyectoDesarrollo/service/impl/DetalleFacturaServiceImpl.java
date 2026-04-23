package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import Grupo4.ProyectoDesarrollo.repository.DetalleFacturaRepository;
import Grupo4.ProyectoDesarrollo.service.DetalleFacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleFacturaServiceImpl implements DetalleFacturaService {

    private final DetalleFacturaRepository repository;

    @Override
    public DetalleFactura crear(DetalleFactura detalleFactura) {
        return repository.save(detalleFactura);
    }

    @Override
    public List<DetalleFactura> listar() {
        return repository.findAll();
    }

    @Override
    public DetalleFactura buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetalleFactura no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
