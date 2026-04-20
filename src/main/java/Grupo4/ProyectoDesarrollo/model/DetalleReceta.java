package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="DetalleReceta")
public class DetalleReceta {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Getter
    @Setter
    @Column(nullable = false)
    private String dosis;

    @Getter
    @Setter
    @Column(nullable = false)
    private String frecuencia;

    @Getter
    @Setter
    @Column(nullable = false)
    private String duracion;

    @Getter
    @Setter
    @Column(nullable = false)
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
