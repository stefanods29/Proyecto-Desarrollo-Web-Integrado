package Grupo4.ProyectoDesarrollo.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String numeroColegiatura;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    @NotNull
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false)
    @NotNull
    private Especialidad especialidad;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    @NotNull
    private Clinica clinica;

    @Column(nullable = false)
    @NotNull
    private Boolean activo = true;
}