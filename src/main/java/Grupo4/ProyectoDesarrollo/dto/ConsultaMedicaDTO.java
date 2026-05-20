package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaMedicaDTO {
    private Long id;
    private Long historiaClinicaId;
    private Long pacienteId;
    private Long medicoId;
    private Long citaId;
    private Long clinicaId;
    private String anamnesis;
    private String examenFisico;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private String presionArterial;
    private Double temperatura;
    private Integer frecuenciaCardiaca;
    private Integer frecuenciaRespiratoria;
    private Double peso;
    private Double talla;
    private LocalDateTime fechaConsulta;
    private LocalDateTime fechaActualizacion;

    public static ConsultaMedicaDTO fromEntity(ConsultaMedica cm) {
        if (cm == null) return null;
        return ConsultaMedicaDTO.builder()
                .id(cm.getId())
                .historiaClinicaId(cm.getHistoriaClinica() != null ? cm.getHistoriaClinica().getId() : null)
                .pacienteId(cm.getPaciente() != null ? cm.getPaciente().getId() : null)
                .medicoId(cm.getMedico() != null ? cm.getMedico().getId() : null)
                .citaId(cm.getCita() != null ? cm.getCita().getId() : null)
                .clinicaId(cm.getClinica() != null ? cm.getClinica().getId() : null)
                .anamnesis(cm.getAnamnesis())
                .examenFisico(cm.getExamenFisico())
                .diagnostico(cm.getDiagnostico())
                .tratamiento(cm.getTratamiento())
                .observaciones(cm.getObservaciones())
                .presionArterial(cm.getPresionArterial())
                .temperatura(cm.getTemperatura())
                .frecuenciaCardiaca(cm.getFrecuenciaCardiaca())
                .frecuenciaRespiratoria(cm.getFrecuenciaRespiratoria())
                .peso(cm.getPeso())
                .talla(cm.getTalla())
                .fechaConsulta(cm.getFechaConsulta())
                .fechaActualizacion(cm.getFechaActualizacion())
                .build();
    }

    public ConsultaMedica toEntity(HistoriaClinica hc, Paciente paciente, Medico medico, Cita cita, Clinica clinica) {
        return new ConsultaMedica(
                this.id,
                paciente,
                medico,
                cita,
                clinica,
                this.anamnesis,
                this.examenFisico,
                this.diagnostico,
                this.tratamiento,
                this.observaciones,
                this.presionArterial,
                this.temperatura,
                this.frecuenciaCardiaca,
                this.frecuenciaRespiratoria,
                this.peso,
                this.talla,
                this.fechaConsulta,
                this.fechaActualizacion
        );
    }
}
