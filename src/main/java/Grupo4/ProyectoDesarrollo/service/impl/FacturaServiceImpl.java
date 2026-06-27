package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.BusinessRuleException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import Grupo4.ProyectoDesarrollo.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository repository;

    @Override
    public Factura crear(Factura factura) {
        if (factura.getId() != null && repository.existsById(factura.getId())) {
            Factura existente = repository.findById(factura.getId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Factura no encontrada con id: " + factura.getId()));

            if (existente.getEstado() == FacturaEstado.PAGADA) {
                throw new BusinessRuleException("No se puede modificar una factura que ya ha sido pagada");
            }

            if (factura.getEstado() == FacturaEstado.PAGADA && existente.getEstado() != FacturaEstado.PAGADA) {
                factura.setFechaPago(LocalDateTime.now());
            } else if (factura.getEstado() != FacturaEstado.PAGADA) {
                factura.setFechaPago(null);
            }
        } else {
            if (factura.getNumeroFactura() == null || factura.getNumeroFactura().trim().isEmpty()) {
                Optional<Factura> lastFacturaOpt = repository.findFirstByOrderByIdDesc();
                String nextNum = "F001-00000001";
                if (lastFacturaOpt.isPresent()) {
                    String lastNum = lastFacturaOpt.get().getNumeroFactura();
                    if (lastNum != null && lastNum.matches("F001-\\d+")) {
                        try {
                            int number = Integer.parseInt(lastNum.substring(5));
                            nextNum = String.format("F001-%08d", number + 1);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                factura.setNumeroFactura(nextNum);
            }

            if (factura.getEstado() == FacturaEstado.PAGADA) {
                factura.setFechaPago(LocalDateTime.now());
            } else {
                factura.setFechaPago(null);
            }
        }

        if (factura.getDetalles() != null) {
            for (DetalleFactura detalle : factura.getDetalles()) {
                detalle.setFactura(factura);
            }
        }

        /*
         * BigDecimal subtotal = BigDecimal.ZERO;
         * if (factura.getDetalles() != null) {
         * for (DetalleFactura detalle : factura.getDetalles()) {
         * if (detalle.getCantidad() == null || detalle.getCantidad() < 1) {
         * throw new
         * BusinessRuleException("La cantidad en el detalle de la factura debe ser mayor a 0"
         * );
         * }
         * if (detalle.getPrecioUnitario() == null ||
         * detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
         * throw new
         * BusinessRuleException("El precio unitario en el detalle de la factura no puede ser negativo"
         * );
         * }
         * BigDecimal detalleTotal =
         * detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()
         * ));
         * detalle.setTotal(detalleTotal);
         * detalle.setFactura(factura);
         * subtotal = subtotal.add(detalleTotal);
         * }
         * }
         * factura.setSubtotal(subtotal);
         * BigDecimal impuesto = subtotal.multiply(BigDecimal.valueOf(0.18)).setScale(2,
         * java.math.RoundingMode.HALF_UP);
         * factura.setImpuesto(impuesto);
         * factura.setTotal(subtotal.add(impuesto).setScale(2,
         * java.math.RoundingMode.HALF_UP));
         */

        return repository.save(factura);
    }

    @Override
    public List<Factura> listar() {
        return repository.findAll();
    }

    @Override
    public Factura buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        Factura existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + id));
        if (existente.getEstado() == FacturaEstado.PAGADA) {
            throw new BusinessRuleException("No se puede eliminar una factura que ya ha sido pagada");
        }
        repository.delete(existente);
    }

    @Override
    public BigDecimal sumTotalFacturadoPorClinicaYEstadoYFechas(Long clinicaId, FacturaEstado estado,
            LocalDateTime inicio, LocalDateTime fin) {
        return repository.sumTotalFacturadoPorClinicaYEstadoYFechas(clinicaId, estado, inicio, fin);
    }
}
