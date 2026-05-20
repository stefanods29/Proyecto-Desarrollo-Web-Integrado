package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "Receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    private List<DetalleReceta> detalles;

    @ManyToOne
    @JoinColumn(name = "consultamedica_id", nullable = false)
    @NotNull
    private ConsultaMedica consultaMedica;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull
    private Paciente paciente;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotNull
    private String indicaciones;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaEmision;

    public Receta() {
    }

    public Receta(Long id, ConsultaMedica consultaMedica, Medico medico, Paciente paciente, String indicaciones,
            List<DetalleReceta> detalles, LocalDateTime fechaEmision) {
        this.id = id;
        this.consultaMedica = consultaMedica;
        this.medico = medico;
        this.paciente = paciente;
        this.indicaciones = indicaciones;
        this.detalles = detalles;
        this.fechaEmision = fechaEmision;
    }

    @PrePersist
    public void prePersist() {
        this.fechaEmision = LocalDateTime.now();
    }
}