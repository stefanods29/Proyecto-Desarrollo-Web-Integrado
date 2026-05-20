package Grupo4.ProyectoDesarrollo.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor 
@Entity
@Table(name="DetalleFactura")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleFactura;

    @Column(nullable = false, length=250)
    @NotNull
    @Size(max=250)
    private String descripcion;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer cantidad;

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name="idFactura", nullable=false)
    @NotNull
    private Factura factura;

}