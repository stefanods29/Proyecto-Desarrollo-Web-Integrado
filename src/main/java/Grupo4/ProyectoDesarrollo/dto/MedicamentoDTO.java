package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Medicamento;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicamentoDTO {
    private Long id;
    private String nombreComercial;
    private String nombreGenerico;
    private String presentacion;
    private String concentracion;
    private String viaAdministracion;
    private Boolean activo;

    public static MedicamentoDTO fromEntity(Medicamento medicamento) {
        if (medicamento == null) return null;
        return MedicamentoDTO.builder()
                .id(medicamento.getId())
                .nombreComercial(medicamento.getNombreComercial())
                .nombreGenerico(medicamento.getNombreGenerico())
                .presentacion(medicamento.getPresentacion())
                .concentracion(medicamento.getConcentracion())
                .viaAdministracion(medicamento.getViaAdministracion())
                .activo(medicamento.getActivo())
                .build();
    }

    public Medicamento toEntity() {
        return new Medicamento(
                this.id,
                this.nombreComercial,
                this.nombreGenerico,
                this.presentacion,
                this.concentracion,
                this.viaAdministracion,
                this.activo != null ? this.activo : true
        );
    }
}
