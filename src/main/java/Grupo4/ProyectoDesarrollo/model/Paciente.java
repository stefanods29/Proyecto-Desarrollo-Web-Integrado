package Grupo4.ProyectoDesarrollo.model;

import jakarta.persistence.*;
import lombok.*;
import Grupo4.ProyectoDesarrollo.model.enums.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPaciente")
    private Long id;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private TipoDocumento tipoDocumento;

    @Column(unique = true, nullable = false, length = 20)
    @NotNull
    @Size(min = 5, max = 20)
    private String numeroDocumento;

    @Column(nullable = false, length = 20)
    @NotNull
    @Size(min = 7, max = 20)
    private String telefono;

    @Column(nullable = false, length = 100)
    @NotNull
    @Email
    @Size(max = 100)
    private String correo;

    @Column(nullable = false, length = 250)
    @NotNull
    @Size(max = 250)
    private String direccion;

    @Column(nullable = false)
    @NotNull
    @Past
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private GrupoSanguineo grupoSanguineo;

    @Column(length = 150)
    @Size(max = 150)
    private String seguroMedico;

    @Column(length = 50)
    @Size(max = 50)
    private String numeroSeguro;

    @Column(length = 150)
    @Size(max = 150)
    private String contactoEmergencia;

    @Column(length = 20)
    @Size(max = 20)
    private String telefonoEmergencia;

    @Column(columnDefinition = "TEXT")
    private String alergias;

    @Column(columnDefinition = "TEXT")
    private String antecedentesPersonales;

    @Column(columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @NotNull
    private Clinica clinica;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}