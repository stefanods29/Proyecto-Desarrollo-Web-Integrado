package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.model.Medico;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioMedicoDTO {
    private Long id;
    private Long medicoId;
    private Long clinicaId;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer duracionTurnoMinutos;
    private Boolean activo;
    private String medicoNombre;
    private String medicoApellido;

    public static HorarioMedicoDTO fromEntity(HorarioMedico hm) {
        if (hm == null)
            return null;
        return HorarioMedicoDTO.builder()
                .id(hm.getId())
                .medicoId(hm.getMedico() != null ? hm.getMedico().getId() : null)
                .medicoNombre(
                        hm.getMedico() != null && hm.getMedico().getUsuario() != null
                                ? hm.getMedico().getUsuario().getNombre()
                                : null)
                .medicoApellido(
                        hm.getMedico() != null && hm.getMedico().getUsuario() != null
                                ? hm.getMedico().getUsuario().getApellido()
                                : null)
                .clinicaId(hm.getClinica() != null ? hm.getClinica().getId() : null)
                .diaSemana(hm.getDiaSemana())
                .horaInicio(hm.getHoraInicio())
                .horaFin(hm.getHoraFin())
                .duracionTurnoMinutos(hm.getDuracionTurnoMinutos())
                .activo(hm.getActivo())
                .build();
    }

    public HorarioMedico toEntity(Medico medico, Clinica clinica) {
        return HorarioMedico.builder()
                .id(this.id)
                .medico(medico)
                .clinica(clinica)
                .diaSemana(this.diaSemana)
                .horaInicio(this.horaInicio)
                .horaFin(this.horaFin)
                .duracionTurnoMinutos(this.duracionTurnoMinutos)
                .activo(this.activo != null ? this.activo : true)
                .build();
    }
}
