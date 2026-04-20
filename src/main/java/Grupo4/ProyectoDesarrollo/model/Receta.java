package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Import;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="Receta")
public class Receta {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    private List<DetalleReceta> detalles;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "consultamedica_id", nullable = false)
    private ConsultaMedica consultaMedica;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Getter
    @Setter
    @Column(nullable = false)
    private String indicaciones;

    @Getter
    @Setter
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    private List<DetalleReceta> detalles;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    public Receta() {
    }

    public Receta(Long id, ConsultaMedica consultaMedica, Medico medico, Paciente paciente, String indicaciones, List<DetalleReceta> detalles, LocalDateTime fechaEmision) {
        this.id = id;
        this.consultaMedica = consultaMedica;
        this.medico = medico;
        this.paciente = paciente;
        this.indicaciones = indicaciones;
        this.detalles = detalles;
        this.fechaEmision = fechaEmision;
    }
}
