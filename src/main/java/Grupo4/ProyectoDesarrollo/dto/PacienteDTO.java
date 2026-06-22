package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.model.enums.Genero;
import Grupo4.ProyectoDesarrollo.model.enums.SeguroMedico;
import Grupo4.ProyectoDesarrollo.model.enums.TipoDocumento;
import Grupo4.ProyectoDesarrollo.model.enums.GrupoSanguineo;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    
    // Identificación y Seguro
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private Genero genero;
    private SeguroMedico seguroMedico;
    private String numeroSeguro;

    // Historial Clínico
    private GrupoSanguineo grupoSanguineo;
    private String contactoEmergencia;
    private String telefonoEmergencia;
    private String alergias;
    private String antecedentesPersonales;
    private String antecedentesFamiliares;
    private LocalDateTime fechaRegistro;

    // IDs de relaciones
    private Long usuarioId;
    private Long clinicaId;

    // =================================================================================
    // 🔥 AQUÍ ESTÁN LOS MÉTODOS QUE FALTABAN PARA QUE EL CONTROLLER FUNCIONE
    // =================================================================================

    // Método para convertir de Paciente (Base de Datos) a PacienteDTO (Frontend)
    public static PacienteDTO fromEntity(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return PacienteDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .correo(paciente.getCorreo())
                .telefono(paciente.getTelefono())
                .direccion(paciente.getDireccion())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .tipoDocumento(paciente.getTipoDocumento())
                .numeroDocumento(paciente.getNumeroDocumento())
                .genero(paciente.getGenero())
                .seguroMedico(paciente.getSeguroMedico())
                .numeroSeguro(paciente.getNumeroSeguro())
                .grupoSanguineo(paciente.getGrupoSanguineo())
                .contactoEmergencia(paciente.getContactoEmergencia())
                .telefonoEmergencia(paciente.getTelefonoEmergencia())
                .alergias(paciente.getAlergias())
                .antecedentesPersonales(paciente.getAntecedentesPersonales())
                .antecedentesFamiliares(paciente.getAntecedentesFamiliares())
                .fechaRegistro(paciente.getFechaRegistro())
                .usuarioId(paciente.getUsuario() != null ? paciente.getUsuario().getId() : null)
                .clinicaId(paciente.getClinica() != null ? paciente.getClinica().getId() : null)
                .build();
    }

    // Método para convertir de PacienteDTO (Frontend) a Paciente (Base de Datos)
    public Paciente toEntity(Clinica clinica, Usuario usuario) {
        return Paciente.builder()
                .id(this.id)
                .nombre(this.nombre)
                .apellido(this.apellido)
                .correo(this.correo)
                .telefono(this.telefono)
                .direccion(this.direccion)
                .fechaNacimiento(this.fechaNacimiento)
                .tipoDocumento(this.tipoDocumento)
                .numeroDocumento(this.numeroDocumento)
                .genero(this.genero)
                .seguroMedico(this.seguroMedico)
                .numeroSeguro(this.numeroSeguro)
                .grupoSanguineo(this.grupoSanguineo)
                .contactoEmergencia(this.contactoEmergencia)
                .telefonoEmergencia(this.telefonoEmergencia)
                .alergias(this.alergias)
                .antecedentesPersonales(this.antecedentesPersonales)
                .antecedentesFamiliares(this.antecedentesFamiliares)
                .fechaRegistro(this.fechaRegistro)
                .usuario(usuario)
                .clinica(clinica)
                .build();
    }
}