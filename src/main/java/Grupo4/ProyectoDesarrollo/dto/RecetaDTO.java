package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaDTO {
    private Long id;
    private Long consultaMedicaId;
    private Long medicoId;
    private Long pacienteId;
    private String indicaciones;
    private LocalDateTime fechaEmision;
    @Builder.Default
    private List<DetalleRecetaDTO> detalles = new ArrayList<>();

    public static RecetaDTO fromEntity(Receta receta) {
        if (receta == null) return null;
        List<DetalleRecetaDTO> detDTOs = new ArrayList<>();
        if (receta.getDetalles() != null) {
            detDTOs = receta.getDetalles().stream()
                    .map(DetalleRecetaDTO::fromEntity)
                    .collect(Collectors.toList());
        }
        return RecetaDTO.builder()
                .id(receta.getId())
                .consultaMedicaId(receta.getConsultaMedica() != null ? receta.getConsultaMedica().getId() : null)
                .medicoId(receta.getMedico() != null ? receta.getMedico().getId() : null)
                .pacienteId(receta.getPaciente() != null ? receta.getPaciente().getId() : null)
                .indicaciones(receta.getIndicaciones())
                .fechaEmision(receta.getFechaEmision())
                .detalles(detDTOs)
                .build();
    }

    public Receta toEntity(ConsultaMedica cm, Medico medico, Paciente paciente) {
        Receta receta = new Receta();
        receta.setId(this.id);
        receta.setConsultaMedica(cm);
        receta.setMedico(medico);
        receta.setPaciente(paciente);
        receta.setIndicaciones(this.indicaciones);
        receta.setFechaEmision(this.fechaEmision);
        // Detalles are populated separately or mapped during entity construction
        return receta;
    }
}
