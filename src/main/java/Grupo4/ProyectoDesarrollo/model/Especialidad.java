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
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    @NotNull
    @Size(min = 3, max = 100)
    private String nombre;

    @Column(length = 500)
    @Size(max = 500)
    private String descripcion;

    @Column(nullable = false)
    @NotNull
    private Boolean activa;
}