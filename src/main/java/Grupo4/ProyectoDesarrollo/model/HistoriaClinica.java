package Grupo4.ProyectoDesarrollo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistoriaClinica")
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @NotNull
    private Clinica clinica;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL)
    private List<ConsultaMedica> consultas;

    public HistoriaClinica(Long id, Paciente paciente, Clinica clinica, LocalDateTime fechaCreacion) {
        this.id = id;
        this.paciente = paciente;
        this.clinica = clinica;
        this.fechaCreacion = fechaCreacion;
    }

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}