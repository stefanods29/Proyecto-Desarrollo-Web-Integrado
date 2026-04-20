package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ArchivoClinico")
public class ArchivoClinico {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "consultamedica_id", nullable = false)
    private ConsultaMedica consultaMedica;

    @Getter
    @Setter
    @Column(nullable = false)
    private String nombreArchivo;

    @Getter
    @Setter
    @Column(nullable = false)
    private String tipoArchivo;

    @Getter
    @Setter
    @Column(nullable = false)
    private String rutaArchivo;

    @Getter
    @Setter
    @Column(nullable = false)
    private String descripcion;

    @Getter
    @Setter
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
