package Grupo4.ProyectoDesarrollo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name="Factura")
public class Factura {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String numeroFactura;

    @Getter
    @Setter
    @Column(nullable=false)
    private BigDecimal subtotal;

    @Getter
    @Setter
    @Column(nullable=false)
    private BigDecimal impuesto;

    @Getter
    @Setter
    @Column(nullable=false)
    private BigDecimal total;

    @Getter
    @Setter
    @Column(nullable=false, length = 200)
    private String estado;

    @Getter
    @Setter
    @Column(nullable=false)
    private String metodoPago;

    @Getter
    @Setter
    @OneToMany(mappedBy="factura")
    private List<DetalleFactura> detalles;
    
    @Getter
    @Setter
    @Column(nullable=false)
    private LocalDateTime fechaEmision;

    @Getter
    @Setter
    @Column(nullable=false)
    private LocalDateTime fechaActualizacion;

    @Getter
    @Setter
    @Column(nullable=false)
    private LocalDateTime fechaPago;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="idPaciente", nullable=false)
    private Paciente paciente;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="idCita", nullable=true)
    private Cita cita;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="idClinica", nullable=false)
    private Clinica clinica;
}
