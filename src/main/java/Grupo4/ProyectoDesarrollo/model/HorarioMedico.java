package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHorarioMedico")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @NotNull
    private Clinica clinica;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private DayOfWeek diaSemana;

    @Column(nullable = false)
    @NotNull
    private LocalTime horaInicio;

    @Column(nullable = false)
    @NotNull
    private LocalTime horaFin;

    @Column(nullable = false)
    @NotNull
    @Min(5)
    @Max(180)
    private Integer duracionTurnoMinutos;

    @Column(nullable = false)
    @NotNull
    private Boolean activo;
}