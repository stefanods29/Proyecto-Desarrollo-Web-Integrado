package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ArchivoClinico")
public class ArchivoClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultamedica_id", nullable = false)
    private ConsultaMedica consultaMedica;

    @Column(nullable = false)
    private String nombreArchivo;

    @Column(nullable = false)
    private String tipoArchivo;

    @Column(nullable = false)
    private String rutaArchivo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
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
}