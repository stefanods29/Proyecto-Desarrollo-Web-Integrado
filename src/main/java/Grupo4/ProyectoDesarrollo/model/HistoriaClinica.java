package Grupo4.ProyectoDesarrollo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HistoriaClinica")
public class HistoriaClinica {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Getter
    @Setter
    @OneToMany(mappedBy = "historiaClinica")
    private List<ConsultaMedica> consultas;

    public HistoriaClinica() {

    }
    public HistoriaClinica(Long id, Paciente paciente,Clinica clinica, LocalDateTime fechaCreacion) {
            this.id = id;
            this.paciente = paciente;
            this.clinica = clinica;
            this.fechaCreacion = fechaCreacion;
        }
    }