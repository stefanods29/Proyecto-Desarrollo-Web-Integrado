package Grupo4.ProyectoDesarrollo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String dni;
    private String telefono;
    private String correo;
}
