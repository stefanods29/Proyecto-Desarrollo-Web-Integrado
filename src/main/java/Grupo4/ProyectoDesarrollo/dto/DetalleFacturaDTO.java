package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import Grupo4.ProyectoDesarrollo.model.Factura;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleFacturaDTO {
    private Long id;
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    
    @Setter(AccessLevel.NONE)
    private BigDecimal total;
    
    private Long facturaId;

    public static DetalleFacturaDTO fromEntity(DetalleFactura df) {
        if (df == null) return null;
        DetalleFacturaDTO dto = DetalleFacturaDTO.builder()
                .id(df.getId())
                .descripcion(df.getDescripcion())
                .cantidad(df.getCantidad())
                .precioUnitario(df.getPrecioUnitario())
                .facturaId(df.getFactura() != null ? df.getFactura().getId() : null)
                .build();
        dto.calcularTotal();
        return dto;
    }

    public DetalleFactura toEntity(Factura factura) {
        DetalleFactura df = new DetalleFactura();
        df.setId(this.id);
        df.setDescripcion(this.descripcion);
        df.setCantidad(this.cantidad);
        df.setPrecioUnitario(this.precioUnitario);
        this.calcularTotal();
        df.setTotal(this.total);
        df.setFactura(factura);
        return df;
    }

    public void calcularTotal() {
        if (this.cantidad != null && this.precioUnitario != null) {
            this.total = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
        } else {
            this.total = BigDecimal.ZERO;
        }
    }
}
