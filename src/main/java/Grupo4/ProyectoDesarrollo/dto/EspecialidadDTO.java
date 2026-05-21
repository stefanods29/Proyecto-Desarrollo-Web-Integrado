package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activa;

    public static EspecialidadDTO fromEntity(Especialidad especialidad) {
        if (especialidad == null) return null;
        return EspecialidadDTO.builder()
                .id(especialidad.getId())
                .nombre(especialidad.getNombre())
                .descripcion(especialidad.getDescripcion())
                .activa(especialidad.getActiva())
                .build();
    }

    public Especialidad toEntity() {
        return Especialidad.builder()
                .id(this.id)
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .activa(this.activa != null ? this.activa : true)
                .build();
    }
}
