package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.MetodoPago;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaDTO {
    private Long id;
    private String numeroFactura;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal total;
    private FacturaEstado estado;
    private MetodoPago metodoPago;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaPago;
    private Long pacienteId;
    private Long citaId;
    private Long clinicaId;

    @Builder.Default
    private List<DetalleFacturaDTO> detalles = new ArrayList<>();

    public static FacturaDTO fromEntity(Factura factura) {
        if (factura == null)
            return null;
        List<DetalleFacturaDTO> detDTOs = new ArrayList<>();
        if (factura.getDetalles() != null) {
            detDTOs = factura.getDetalles().stream()
                    .map(DetalleFacturaDTO::fromEntity)
                    .collect(Collectors.toList());
        }
        FacturaDTO dto = FacturaDTO.builder()
                .id(factura.getId())
                .numeroFactura(factura.getNumeroFactura())
                .subtotal(factura.getSubtotal())
                .impuesto(factura.getImpuesto())
                .total(factura.getTotal())
                .estado(factura.getEstado())
                .metodoPago(factura.getMetodoPago())
                .fechaEmision(factura.getFechaEmision())
                .fechaActualizacion(factura.getFechaActualizacion())
                .fechaPago(factura.getFechaPago())
                .pacienteId(factura.getPaciente() != null ? factura.getPaciente().getId() : null)
                .citaId(factura.getCita() != null ? factura.getCita().getId() : null)
                .clinicaId(factura.getClinica() != null ? factura.getClinica().getId() : null)
                .detalles(detDTOs)
                .build();

        return dto;
    }

    public Factura toEntity(Paciente paciente, Cita cita, Clinica clinica) {
        Factura factura = new Factura();
        factura.setId(this.id);
        factura.setNumeroFactura(this.numeroFactura);
        factura.setSubtotal(this.subtotal);
        factura.setImpuesto(this.impuesto);
        factura.setTotal(this.total);
        factura.setEstado(this.estado);
        factura.setMetodoPago(this.metodoPago);
        factura.setFechaEmision(this.fechaEmision);
        factura.setFechaActualizacion(this.fechaActualizacion);
        factura.setFechaPago(this.fechaPago);
        factura.setPaciente(paciente);
        factura.setCita(cita);
        factura.setClinica(clinica);

        if (this.detalles != null && !this.detalles.isEmpty()) {
            List<DetalleFactura> listaDetalles = new ArrayList<>();
            for (DetalleFacturaDTO dtoDetalle : this.detalles) {
                DetalleFactura d = new DetalleFactura();
                d.setId(dtoDetalle.getId());
                d.setDescripcion(dtoDetalle.getDescripcion());
                d.setCantidad(dtoDetalle.getCantidad());
                d.setPrecioUnitario(dtoDetalle.getPrecioUnitario());
                d.setTotal(dtoDetalle.getTotal());
                d.setFactura(factura); // Crucial para la BD
                listaDetalles.add(d);
            }
            factura.setDetalles(listaDetalles);
        }
        return factura;
    }

    public void calcularTotales() {
        BigDecimal accumSubtotal = BigDecimal.ZERO;
        if (this.detalles != null) {
            for (DetalleFacturaDTO d : this.detalles) {
                d.calcularTotal();
                if (d.getTotal() != null) {
                    accumSubtotal = accumSubtotal.add(d.getTotal());
                }
            }
        }
        this.subtotal = accumSubtotal;
        this.impuesto = this.subtotal.multiply(BigDecimal.valueOf(0.18)).setScale(2, java.math.RoundingMode.HALF_UP);
        this.total = this.subtotal.add(this.impuesto).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
