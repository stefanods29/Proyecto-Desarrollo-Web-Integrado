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
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;

    private Long especialidadId;
    private Long clinicaId;
    private Boolean activo;

    public static MedicoDTO fromEntity(Medico medico) {
        if (medico == null) return null;

        Usuario usuario = medico.getUsuario();

        return MedicoDTO.builder()
                .id(medico.getId())
                .numeroColegiatura(medico.getNumeroColegiatura())

                .usuarioId(usuario != null ? usuario.getId() : null)
                .nombre(usuario != null ? usuario.getNombre() : null)
                .apellido(usuario != null ? usuario.getApellido() : null)
                .correo(usuario != null ? usuario.getCorreo() : null)
                .telefono(usuario != null ? usuario.getTelefono() : null)

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