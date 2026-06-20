package Grupo4.ProyectoDesarrollo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicoRankingDTO {
    private Long medicoId;
    private String nombre;
    private String apellido;
    private Long totalConsultas;

    public static MedicoRankingDTO fromObjectArray(Object[] row) {
        Long medicoId = row[0] != null ? ((Number) row[0]).longValue() : null;
        String nombre = row[1] != null ? row[1].toString() : null;
        String apellido = row[2] != null ? row[2].toString() : null;
        Long totalConsultas = row[3] != null ? ((Number) row[3]).longValue() : null;
        return new MedicoRankingDTO(medicoId, nombre, apellido, totalConsultas);
    }
}
