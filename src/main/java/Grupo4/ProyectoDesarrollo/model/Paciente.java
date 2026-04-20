package Grupo4.ProyectoDesarrollo.model;

import Grupo4.ProyectoDesarrollo.model.enums.Genero;
import Grupo4.ProyectoDesarrollo.model.enums.GrupoSanguineo;
import Grupo4.ProyectoDesarrollo.model.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
public class Paciente {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String nombre;

    @Getter
    @Setter
    @Column(nullable = false)
    private String apellido;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDocumento tipoDocumento;

    @Getter
    @Setter
    @Column(nullable = false)
    private String numeroDocumento;

    @Getter
    @Setter
    private String telefono;

    @Getter
    @Setter
    private String correo;

    @Getter
    @Setter
    private String direccion;

    @Getter
    @Setter
    private LocalDate fechaNacimiento;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GrupoSanguineo grupoSanguineo;

    @Getter
    @Setter
    private String seguroMedico;

    @Getter
    @Setter
    private String numeroSeguro;

    @Getter
    @Setter
    private String contactoEmergencia;

    @Getter
    @Setter
    private String telefonoEmergencia;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String alergias;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String antecedentesPersonales;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Getter
    @Setter
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    public Paciente() {
    }

    
}
