package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

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
    private HistoriaClinica historiaClinica;

    @OneToMany(mappedBy = "consultaMedica")
    private List<Receta> recetas;

    @OneToMany(mappedBy = "consultaMedica")
    private List<ArchivoClinico> archivos;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    @Column(columnDefinition = "TEXT")
    private String anamnesis;

    @Column(columnDefinition = "TEXT")
    private String examenFisico;

    @Column(nullable = false)
    private String diagnostico;

    @Column(nullable = false)
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(nullable = false)
    private String presionArterial;

    @Column(nullable = false)
    private Double temperatura;

    @Column(nullable = false)
    private Integer frecuenciaCardiaca;

    @Column(nullable = false)
    private Integer frecuenciaRespiratoria;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private Double talla;

    @Column(nullable = false)
    private LocalDateTime fechaConsulta;

    @Column(nullable = false)
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
}