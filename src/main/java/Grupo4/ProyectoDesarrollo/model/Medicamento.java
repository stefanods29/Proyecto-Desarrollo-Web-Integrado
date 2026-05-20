package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import jakarta.validation.constraints.*;

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

    @Column(nullable = false, length = 150)
    @NotNull
    @Size(min = 1, max = 150)
    private String nombreComercial;

    @Column(nullable = false, length = 150)
    @NotNull
    @Size(min = 1, max = 150)
    private String nombreGenerico;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String presentacion;

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String concentracion;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String viaAdministracion;

    @Column(nullable = false)
    @NotNull
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