package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArchivoClinicoDTO {
    private Long id;
    private Long consultaMedicaId;
    private String nombreArchivo;
    private String tipoArchivo;
    private String rutaArchivo;
    private String description; 
    private LocalDateTime fechaSubida;

    public static ArchivoClinicoDTO fromEntity(ArchivoClinico ac) {
        if (ac == null) return null;
        return ArchivoClinicoDTO.builder()
                .id(ac.getId())
                .consultaMedicaId(ac.getConsultaMedica() != null ? ac.getConsultaMedica().getId() : null)
                .nombreArchivo(ac.getNombreArchivo())
                .tipoArchivo(ac.getTipoArchivo())
                .rutaArchivo(ac.getRutaArchivo())
                .description(ac.getDescripcion())
                .fechaSubida(ac.getFechaSubida())
                .build();
    }

    public ArchivoClinico toEntity(ConsultaMedica cm) {
        return new ArchivoClinico(
                this.id,
                cm,
                this.nombreArchivo,
                this.tipoArchivo,
                this.rutaArchivo,
                this.description,
                this.fechaSubida
        );
    }
}
