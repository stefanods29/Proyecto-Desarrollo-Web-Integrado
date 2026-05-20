package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name="DetalleReceta")
public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    @NotNull
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    @NotNull
    private Medicamento medicamento;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String dosis;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String frecuencia;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String duracion;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotNull
    private String instrucciones;

    public DetalleReceta() {
    }

    public DetalleReceta(Long id, Receta receta, Medicamento medicamento, String dosis, String frecuencia, String duracion, String instrucciones) {
        this.id = id;
        this.receta = receta;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.instrucciones = instrucciones;
    }
}