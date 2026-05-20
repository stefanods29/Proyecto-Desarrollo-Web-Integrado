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
@Table(name = "ConsultaMedica")
public class ConsultaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HistoriaClinica_id", nullable = false)
    @NotNull
    private HistoriaClinica historiaClinica;

    @OneToMany(mappedBy = "consultaMedica")
    private List<Receta> recetas;

    @OneToMany(mappedBy = "consultaMedica")
    private List<ArchivoClinico> archivos;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @NotNull
    private Clinica clinica;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotNull
    private String anamnesis;

    @Column(columnDefinition = "TEXT")
    private String examenFisico;

    @Column(nullable = false, length = 500)
    @NotNull
    @Size(max = 500)
    private String diagnostico;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotNull
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    @Size(max = 1000)
    private String observaciones;

    @Column(nullable = false, length = 20)
    @NotNull
    @Size(max = 20)
    private String presionArterial;

    @Column(nullable = false)
    @NotNull
    @DecimalMin("30.0")
    @DecimalMax("45.0")
    private Double temperatura;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Max(300)
    private Integer frecuenciaCardiaca;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Max(100)
    private Integer frecuenciaRespiratoria;

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("500.0")
    private Double peso;

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("3.0")
    private Double talla;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaConsulta;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime fechaActualizacion;

    public ConsultaMedica() {}

    public ConsultaMedica(Long id, Paciente paciente, Medico medico, Cita cita, Clinica clinica, String anamnesis, String examenFisico, String diagnostico, String tratamiento, String observaciones, String presionArterial, Double temperatura, Integer frecuenciaCardiaca, Integer frecuenciaRespiratoria, Double peso, Double talla, LocalDateTime fechaConsulta, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.cita = cita;
        this.clinica = clinica;
        this.anamnesis = anamnesis;
        this.examenFisico = examenFisico;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.presionArterial = presionArterial;
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.peso = peso;
        this.talla = talla;
        this.fechaConsulta = fechaConsulta;
        this.fechaActualizacion = fechaActualizacion;
    }

    @PrePersist
    public void prePersist() {
        this.fechaConsulta = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}