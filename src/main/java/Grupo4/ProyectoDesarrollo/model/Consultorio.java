package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConsultorio")
    private Long id;

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String ubicacion;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer capacidad;

    @Column(nullable = false)
    @NotNull
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @NotNull
    private Clinica clinica;
}