package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaClinicaDTO {
    private Long id;
    private Long pacienteId;
    private Long clinicaId;
    private LocalDateTime fechaCreacion;

    public static HistoriaClinicaDTO fromEntity(HistoriaClinica hc) {
        if (hc == null) return null;
        return HistoriaClinicaDTO.builder()
                .id(hc.getId())
                .pacienteId(hc.getPaciente() != null ? hc.getPaciente().getId() : null)
                .clinicaId(hc.getClinica() != null ? hc.getClinica().getId() : null)
                .fechaCreacion(hc.getFechaCreacion())
                .build();
    }

    public HistoriaClinica toEntity(Paciente paciente, Clinica clinica) {
        return HistoriaClinica.builder()
                .id(this.id)
                .paciente(paciente)
                .clinica(clinica)
                .fechaCreacion(this.fechaCreacion)
                .build();
    }
}
