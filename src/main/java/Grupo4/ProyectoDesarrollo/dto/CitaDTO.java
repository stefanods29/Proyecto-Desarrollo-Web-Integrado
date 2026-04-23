package Grupo4.ProyectoDesarrollo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class citaDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime fechaHora;
    private String estado;

}
