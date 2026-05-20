package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Consultorio;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultorioDTO {
    private Long id;
    private String nombre;
    private String ubicacion;
    private Integer capacidad;
    private Boolean activo;
    private Long clinicaId;

    public static ConsultorioDTO fromEntity(Consultorio consultorio) {
        if (consultorio == null) return null;
        return ConsultorioDTO.builder()
                .id(consultorio.getId())
                .nombre(consultorio.getNombre())
                .ubicacion(consultorio.getUbicacion())
                .capacidad(consultorio.getCapacidad())
                .activo(consultorio.getActivo())
                .clinicaId(consultorio.getClinica() != null ? consultorio.getClinica().getId() : null)
                .build();
    }

    public Consultorio toEntity(Clinica clinica) {
        return Consultorio.builder()
                .id(this.id)
                .nombre(this.nombre)
                .ubicacion(this.ubicacion)
                .capacidad(this.capacidad)
                .activo(this.activo != null ? this.activo : true)
                .clinica(clinica)
                .build();
    }
}
