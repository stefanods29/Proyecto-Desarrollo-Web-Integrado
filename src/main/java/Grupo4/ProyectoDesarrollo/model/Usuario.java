package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import Grupo4.ProyectoDesarrollo.model.enums.Rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(nullable = false)
    @NotNull
    @Size(min = 6, max = 255)
    private String password;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    private String nombre;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    private String apellido;

    @Column(unique = true, nullable = false)
    @NotNull
    @Email
    @Size(max = 100)
    private String correo;

    @Column(nullable = false)
    @NotNull
    @Size(min = 7, max = 20)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Rol rol;

    @Builder.Default
    @Column(nullable = false)
    @NotNull
    private Boolean activo = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}