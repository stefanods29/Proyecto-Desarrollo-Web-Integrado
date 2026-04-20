package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
public class Cita {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "consultorio_id")
    private Consultorio consultorio;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Getter
    @Setter
    private LocalDateTime fechaFin;

    @Getter
    @Setter
    @Column(nullable = false)
    private String estado;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String motivo;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String notas;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    @Getter
    @Setter
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    public Cita() {
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }
}
