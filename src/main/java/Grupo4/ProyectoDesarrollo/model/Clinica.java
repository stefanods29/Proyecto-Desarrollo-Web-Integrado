package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import Grupo4.ProyectoDesarrollo.model.enums.ClinicaEstado;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "clinica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    @NotNull
    @Size(min = 3, max = 150)
    private String nombre;

    @Column(unique = true, nullable = false, length = 11)
    @NotNull
    @Size(min = 11, max = 11)
    private String ruc;

    @Column(nullable = false, length = 250)
    @NotNull
    @Size(min = 5, max = 250)
    private String direccion;

    @Column(nullable = false, length = 20)
    @NotNull
    @Size(min = 7, max = 20)
    private String telefono;

    @Column(unique = true, nullable = false, length = 100)
    @NotNull
    @Email
    @Size(max = 100)
    private String correo;

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String planSuscripcion; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private ClinicaEstado estado; 

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}