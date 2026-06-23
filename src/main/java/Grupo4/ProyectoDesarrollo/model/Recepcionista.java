package Grupo4.ProyectoDesarrollo.model;

import Grupo4.ProyectoDesarrollo.model.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "recepcionista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recepcionista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1 a 1: Si borran al recepcionista, se borra su usuario del sistema
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nombre;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String apellido;

    @Column(nullable = false, length = 20, unique = true)
    @NotBlank
    private String dni;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100, unique = true)
    @Email
    private String correo;
}