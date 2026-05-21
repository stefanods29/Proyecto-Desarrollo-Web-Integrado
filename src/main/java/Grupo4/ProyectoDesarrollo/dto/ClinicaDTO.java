package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.enums.ClinicaEstado;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicaDTO {
    private Long id;
    private String nombre;
    private String ruc;
    private String direccion;
    private String telefono;
    private String correo;
    private String planSuscripcion;
    private ClinicaEstado estado;
    private LocalDateTime fechaRegistro;

    public static ClinicaDTO fromEntity(Clinica clinica) {
        if (clinica == null) return null;
        return ClinicaDTO.builder()
                .id(clinica.getId())
                .nombre(clinica.getNombre())
                .ruc(clinica.getRuc())
                .direccion(clinica.getDireccion())
                .telefono(clinica.getTelefono())
                .correo(clinica.getCorreo())
                .planSuscripcion(clinica.getPlanSuscripcion())
                .estado(clinica.getEstado())
                .fechaRegistro(clinica.getFechaRegistro())
                .build();
    }

    public Clinica toEntity() {
        return Clinica.builder()
                .id(this.id)
                .nombre(this.nombre)
                .ruc(this.ruc)
                .direccion(this.direccion)
                .telefono(this.telefono)
                .correo(this.correo)
                .planSuscripcion(this.planSuscripcion)
                .estado(this.estado)
                .fechaRegistro(this.fechaRegistro)
                .build();
    }
}
