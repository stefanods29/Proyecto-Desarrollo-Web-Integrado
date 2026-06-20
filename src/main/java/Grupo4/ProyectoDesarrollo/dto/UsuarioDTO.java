package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.model.enums.Rol;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private Rol rol;
    private Boolean activo;
    private Long clinicaId;
    private LocalDateTime fechaCreacion;

    public static UsuarioDTO fromEntity(Usuario usuario) {
        if (usuario == null) return null;
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .telefono(usuario.getTelefono())
                .rol(usuario.getRol())
                .activo(usuario.getActivo())
                .clinicaId(usuario.getClinica() != null ? usuario.getClinica().getId() : null)
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }

    public Usuario toEntity(Clinica clinica) {
        return Usuario.builder()
                .id(this.id)
                .username(this.username)
                .nombre(this.nombre)
                .apellido(this.apellido)
                .correo(this.correo)
                .telefono(this.telefono)
                .rol(this.rol)
                .activo(this.activo != null ? this.activo : true)
                .clinica(clinica)
                .fechaCreacion(this.fechaCreacion)
                .build();
    }
}
