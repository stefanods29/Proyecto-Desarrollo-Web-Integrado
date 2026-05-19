package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="Medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "medicamento")
    private List<DetalleReceta> detalleRecetas;

    @Column (nullable = false)
    private String nombreComercial;

    @Column(nullable = false)
    private String nombreGenerico;

    @Column(nullable = false)
    private String presentacion;

    @Column(nullable = false)
    private String concentracion;

    @Column(nullable = false)
    private String viaAdministracion;

    @Column(nullable = false)
    private Boolean activo;

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