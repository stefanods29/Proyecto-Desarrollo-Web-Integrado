package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de factura no encontrado con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de factura no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<DetalleFactura> buscarPorFacturaId(Long facturaId) {
        return repository.findByFacturaId(facturaId);
    }

    @Override
    public List<DetalleFactura> buscarPorDescripcion(String descripcion) {
        return repository.findByDescripcionContainingIgnoreCase(descripcion);
    }

    @Override
    public List<DetalleFactura> buscarPorClinicaId(Long clinicaId) {
        return repository.buscarDetallesPorClinicaId(clinicaId);
    }
}
