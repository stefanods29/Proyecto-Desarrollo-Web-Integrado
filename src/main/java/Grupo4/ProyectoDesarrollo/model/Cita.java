package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "consultorio_id")
    private Consultorio consultorio;

    private LocalDateTime fechaHora;

    private LocalDateTime fechaFin;

    private String estado;

    private String motivo;

    @Column(columnDefinition = "TEXT")
    private String notas;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    private LocalDateTime fechaCreacion;
}
