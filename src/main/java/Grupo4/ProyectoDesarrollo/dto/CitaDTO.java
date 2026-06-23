package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private Long consultorioId;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaFin;
    private CitaEstado estado;
    private String motivo;
    private String notas;
    private Long clinicaId;
    private LocalDateTime fechaCreacion;

    public static CitaDTO fromEntity(Cita cita) {
        if (cita == null)
            return null;
        return CitaDTO.builder()
                .id(cita.getId())
                .pacienteId(cita.getPaciente() != null ? cita.getPaciente().getId() : null)
                .medicoId(cita.getMedico() != null ? cita.getMedico().getId() : null)
                .consultorioId(cita.getConsultorio() != null ? cita.getConsultorio().getId() : null)
                .fechaHora(cita.getFechaHora())
                .fechaFin(cita.getFechaFin())
                .estado(cita.getEstado())
                .motivo(cita.getMotivo())
                .notas(cita.getNotas())
                .clinicaId(cita.getClinica() != null ? cita.getClinica().getId() : null)
                .fechaCreacion(cita.getFechaCreacion())
                .build();
    }

    public Cita toEntity(Paciente paciente, Medico medico, Consultorio consultorio, Clinica clinica) {
        return Cita.builder()
                .id(this.id)
                .paciente(paciente)
                .medico(medico)
                .consultorio(consultorio)
                .fechaHora(this.fechaHora)
                .fechaFin(this.fechaFin)
                .estado(this.estado)
                .motivo(this.motivo)
                .notas(this.notas)
                .clinica(clinica)
                .fechaCreacion(this.fechaCreacion)
                .build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public Long getConsultorioId() {
        return consultorioId;
    }

    public void setConsultorioId(Long consultorioId) {
        this.consultorioId = consultorioId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public CitaEstado getEstado() {
        return estado;
    }

    public void setEstado(CitaEstado estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Long getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(Long clinicaId) {
        this.clinicaId = clinicaId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
