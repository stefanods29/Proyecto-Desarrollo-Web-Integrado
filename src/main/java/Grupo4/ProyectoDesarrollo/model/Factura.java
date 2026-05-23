package Grupo4.ProyectoDesarrollo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.MetodoPago;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactura")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String numeroFactura;

    @Column(nullable=false)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal subtotal;

    @Column(nullable=false)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal impuesto;

    @Column(nullable=false)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length = 200)
    @NotNull
    private FacturaEstado estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @NotNull
    private MetodoPago metodoPago;

    @OneToMany(mappedBy="factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;
    
    @Column(nullable=false, updatable = false)
    @NotNull
    private LocalDateTime fechaEmision;

    @Column(nullable=false)
    @NotNull
    private LocalDateTime fechaActualizacion;

    @Column(nullable=true)
    private LocalDateTime fechaPago;

    @ManyToOne
    @JoinColumn(name="idPaciente", nullable=false)
    @NotNull
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name="idCita", nullable=true)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name="idClinica", nullable=false)
    @NotNull
    private Clinica clinica;

    @PrePersist
    public void prePersist() {
        this.fechaEmision = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}