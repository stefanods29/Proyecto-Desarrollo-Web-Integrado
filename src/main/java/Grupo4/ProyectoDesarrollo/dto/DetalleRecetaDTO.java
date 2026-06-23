package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.model.Receta;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleRecetaDTO {
    private Long id;
    private Long recetaId;
    private Long medicamentoId;
    
    // 🔥 NUEVO: Objeto anidado para llevar los datos del medicamento a Angular
    private MedicamentoDTO medicamento;

    private String dosis;
    private String frecuencia;
    private String duracion;
    private String instrucciones;

    public static DetalleRecetaDTO fromEntity(DetalleReceta dr) {
        if (dr == null) return null;
        
        return DetalleRecetaDTO.builder()
                .id(dr.getId())
                .recetaId(dr.getReceta() != null ? dr.getReceta().getId() : null)
                .medicamentoId(dr.getMedicamento() != null ? dr.getMedicamento().getId() : null)
                // Cargar toda la info del medicamento para el Frontend:
                .medicamento(dr.getMedicamento() != null ? MedicamentoDTO.fromEntity(dr.getMedicamento()) : null)
                .dosis(dr.getDosis())
                .frecuencia(dr.getFrecuencia())
                .duracion(dr.getDuracion())
                .instrucciones(dr.getInstrucciones())
                .build();
    }

    public DetalleReceta toEntity(Receta receta, Medicamento medicamento) {
        return new DetalleReceta(
                this.id,
                receta,
                medicamento,
                this.dosis,
                this.frecuencia,
                this.duracion,
                this.instrucciones
        );
    }
}