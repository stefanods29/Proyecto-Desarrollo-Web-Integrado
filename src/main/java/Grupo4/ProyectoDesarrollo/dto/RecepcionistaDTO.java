package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Recepcionista;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionistaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;

    // Relaciones clave
    private Long clinicaId;
    private String clinicaNombre; 
    
    // Datos de Autenticación
    private Long usuarioId;
    private String username;
    private String password; // Se usa solo al CREAR (Angular -> Java), no se devuelve
    private String rol;

    public static RecepcionistaDTO fromEntity(Recepcionista r) {
        if (r == null) return null;

        return RecepcionistaDTO.builder()
                .id(r.getId())
                .nombre(r.getNombre())
                .apellido(r.getApellido())
                .dni(r.getDni())
                .telefono(r.getTelefono())
                .correo(r.getCorreo())
                .clinicaId(r.getClinica() != null ? r.getClinica().getId() : null)
                .clinicaNombre(r.getClinica() != null ? r.getClinica().getNombre() : null)
                .usuarioId(r.getUsuario() != null ? r.getUsuario().getId() : null)
                .username(r.getUsuario() != null ? r.getUsuario().getUsername() : null)
                .rol(r.getUsuario() != null ? r.getUsuario().getRol().name() : null)
                .build();
    }
}