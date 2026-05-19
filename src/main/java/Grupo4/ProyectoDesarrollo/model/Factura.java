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
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
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
    private Long idFactura;

    @Column(unique = true, nullable = false)
    private String numeroFactura;

    @Column(nullable=false)
    private BigDecimal subtotal;

    @Column(nullable=false)
    private BigDecimal impuesto;

    @Column(nullable=false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length = 200)
    private FacturaEstado estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private MetodoPago metodoPago;

    @OneToMany(mappedBy="factura")
    private List<DetalleFactura> detalles;
    
    @Column(nullable=false)
    private LocalDateTime fechaEmision;

    @Column(nullable=false)
    private LocalDateTime fechaActualizacion;

    @Column(nullable=false)
    private LocalDateTime fechaPago;

    @ManyToOne
    @JoinColumn(name="idPaciente", nullable=false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name="idCita", nullable=true)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name="idClinica", nullable=false)
    private Clinica clinica;
}