package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCita")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "consultorio_id", nullable = false)
    @NotNull
    private Consultorio consultorio;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    @NotNull
    private CitaEstado estado;

    @Column(nullable = false, length = 250)
    @NotNull
    @Size(max = 250)
    private String motivo;

    @Column(columnDefinition = "TEXT")
    @Size(max = 1000)
    private String notas;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @NotNull
    private Clinica clinica;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}