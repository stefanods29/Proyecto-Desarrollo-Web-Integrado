package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "ArchivoClinico")
public class ArchivoClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArchivoClinico")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultamedica_id", nullable = false)
    @NotNull
    private ConsultaMedica consultaMedica;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(min = 1, max = 255)
    private String nombreArchivo;

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String tipoArchivo;

    @Column(nullable = false, length = 500)
    @NotNull
    @Size(max = 500)
    private String rutaArchivo;

    @Column(nullable = false, length = 500)
    @NotNull
    @Size(max = 500)
    private String descripcion;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaSubida;

    public ArchivoClinico() {

    }

    public ArchivoClinico(Long id, ConsultaMedica consultaMedica, String nombreArchivo, String tipoArchivo, String rutaArchivo, String descripcion, LocalDateTime fechaSubida) {
        this.id = id;
        this.consultaMedica = consultaMedica;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.rutaArchivo = rutaArchivo;
        this.descripcion = descripcion;
        this.fechaSubida = fechaSubida;
    }

    @PrePersist
    public void prePersist() {
        this.fechaSubida = LocalDateTime.now();
    }
}