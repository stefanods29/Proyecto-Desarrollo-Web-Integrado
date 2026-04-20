package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Medicamento")
public class Medicamento {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @OneToMany(mappedBy = "medicamento")
    private List<DetalleReceta> detalleRecetas;

    @Getter
    @Setter
    @Column (nullable = false)
    private String nombreComercial;

    @Getter
    @Setter
    @Column(nullable = false)
    private String nombreGenerico;

    @Getter
    @Setter
    @Column(nullable = false)
    private String presentacion;

    @Getter
    @Setter
    @Column(nullable = false)
    private String concentracion;

    @Getter
    @Setter
    @Column(nullable = false)
    private String viaAdministracion;

    @Getter
    @Setter
    @Column(nullable = false)
    private boolean activo;

    public Medicamento() {
    }

    public Medicamento(Long id, String nombreComercial, String nombreGenerico, String presentacion, String concentracion, String viaAdministracion, boolean activo) {
        this.id = id;
        this.nombreComercial = nombreComercial;
        this.nombreGenerico = nombreGenerico;
        this.presentacion = presentacion;
        this.concentracion = concentracion;
        this.viaAdministracion = viaAdministracion;
        this.activo = activo;
    }
}
