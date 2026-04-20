package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "ConsultaMedica")
public class ConsultaMedica {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "HistoriaClinica_id", nullable = false)
    private HistoriaClinica historiaClinica;

    @Getter
    @Setter
    @OneToMany(mappedBy = "consultaMedica")
    private List<Receta> recetas;

    @Getter
    @Setter
    @OneToMany(mappedBy = "consultaMedica")
    private List<ArchivoClinico> archivos;

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
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String anamnesis;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String examenFisico;

    @Getter
    @Setter
    @Column(nullable = false)
    private String diagnostico;

    @Getter
    @Setter
    @Column(nullable = false)
    private String tratamiento;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Getter
    @Setter
    @Column(nullable = false)
    private String presionArterial;

    @Getter
    @Setter
    @Column(nullable = false)
    private Double temperatura;

    @Getter
    @Setter
    @Column(nullable = false)
    private Integer frecuenciaCardiaca;

    @Getter
    @Setter
    @Column(nullable = false)
    private Integer frecuenciaRespiratoria;

    @Getter
    @Setter
    @Column(nullable = false)
    private Double peso;

    @Getter
    @Setter
    @Column(nullable = false)
    private Double talla;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime fechaConsulta;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime fechaActualizada;

    public ConsultaMedica() {}

    public ConsultaMedica(Long id, Paciente paciente, Medico medico, Cita cita, Clinica clinica, String anamnesis, String examenFisico, String diagnostico, String tratamiento, String observaciones, String presionArterial, Double temperatura, Integer frecuenciaCardiaca, Integer frecuenciaRespiratoria, Double peso, Double talla, LocalDateTime fechaConsulta, LocalDateTime fechaActualizada) {
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
        this.fechaActualizada = fechaActualizada;
    }
}
