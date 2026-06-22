package Grupo4.ProyectoDesarrollo.model;

import Grupo4.ProyectoDesarrollo.model.enums.Genero;
import Grupo4.ProyectoDesarrollo.model.enums.SeguroMedico;
import Grupo4.ProyectoDesarrollo.model.enums.TipoDocumento;
import Grupo4.ProyectoDesarrollo.model.enums.GrupoSanguineo; 

import jakarta.persistence.*;
import lombok.*;
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
    private Long id;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 100)
    private String correo;

    @Column(length = 20)
    private String telefono;

    private String direccion;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    // --- ENUMS Y DOCUMENTOS ---
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "seguro_medico")
    private SeguroMedico seguroMedico;

    @Column(name = "numero_seguro")
    private String numeroSeguro;

    // --- CAMPOS MÉDICOS RESTAURADOS ---
    @Enumerated(EnumType.STRING)
    @Column(name = "grupo_sanguineo")
    private GrupoSanguineo grupoSanguineo;

    @Column(name = "contacto_emergencia", length = 100)
    private String contactoEmergencia;

    @Column(name = "telefono_emergencia", length = 20)
    private String telefonoEmergencia;

    @Column(columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "antecedentes_personales", columnDefinition = "TEXT")
    private String antecedentesPersonales;

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro; 

    // --- RELACIONES ---
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
}