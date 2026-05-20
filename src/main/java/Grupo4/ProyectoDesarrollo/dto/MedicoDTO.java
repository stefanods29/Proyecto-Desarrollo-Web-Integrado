package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoDTO {
    private Long id;
    private String numeroColegiatura;
    private Long usuarioId;
    private Long especialidadId;
    private Long clinicaId;
    private Boolean activo;

    public static MedicoDTO fromEntity(Medico medico) {
        if (medico == null) return null;
        return MedicoDTO.builder()
                .id(medico.getId())
                .numeroColegiatura(medico.getNumeroColegiatura())
                .usuarioId(medico.getUsuario() != null ? medico.getUsuario().getId() : null)
                .especialidadId(medico.getEspecialidad() != null ? medico.getEspecialidad().getId() : null)
                .clinicaId(medico.getClinica() != null ? medico.getClinica().getId() : null)
                .activo(medico.getActivo())
                .build();
    }

    public Medico toEntity(Usuario usuario, Especialidad especialidad, Clinica clinica) {
        Medico medico = new Medico();
        medico.setId(this.id);
        medico.setNumeroColegiatura(this.numeroColegiatura);
        medico.setUsuario(usuario);
        medico.setEspecialidad(especialidad);
        medico.setClinica(clinica);
        medico.setActivo(this.activo != null ? this.activo : true);
        return medico;
    }
}