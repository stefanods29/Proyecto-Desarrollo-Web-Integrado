package Grupo4.ProyectoDesarrollo.dto;

import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.model.enums.*;
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
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String telefono;
    private String correo;
    private String direccion;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private GrupoSanguineo grupoSanguineo;
    private String seguroMedico;
    private String numeroSeguro;
    private String contactoEmergencia;
    private String telefonoEmergencia;
    private String alergias;
    private String antecedentesPersonales;
    private String antecedentesFamiliares;
    private Long clinicaId;
    private Long usuarioId;
    private LocalDateTime fechaRegistro;

    public static PacienteDTO fromEntity(Paciente paciente) {
        if (paciente == null) return null;
        return PacienteDTO.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .tipoDocumento(paciente.getTipoDocumento())
                .numeroDocumento(paciente.getNumeroDocumento())
                .telefono(paciente.getTelefono())
                .correo(paciente.getCorreo())
                .direccion(paciente.getDireccion())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .genero(paciente.getGenero())
                .grupoSanguineo(paciente.getGrupoSanguineo())
                .seguroMedico(paciente.getSeguroMedico())
                .numeroSeguro(paciente.getNumeroSeguro())
                .contactoEmergencia(paciente.getContactoEmergencia())
                .telefonoEmergencia(paciente.getTelefonoEmergencia())
                .alergias(paciente.getAlergias())
                .antecedentesPersonales(paciente.getAntecedentesPersonales())
                .antecedentesFamiliares(paciente.getAntecedentesFamiliares())
                .clinicaId(paciente.getClinica() != null ? paciente.getClinica().getId() : null)
                .usuarioId(paciente.getUsuario() != null ? paciente.getUsuario().getId() : null)
                .fechaRegistro(paciente.getFechaRegistro())
                .build();
    }

    public Paciente toEntity(Clinica clinica, Usuario usuario) {
        return Paciente.builder()
                .id(this.id)
                .nombre(this.nombre)
                .apellido(this.apellido)
                .tipoDocumento(this.tipoDocumento)
                .numeroDocumento(this.numeroDocumento)
                .telefono(this.telefono)
                .correo(this.correo)
                .direccion(this.direccion)
                .fechaNacimiento(this.fechaNacimiento)
                .genero(this.genero)
                .grupoSanguineo(this.grupoSanguineo)
                .seguroMedico(this.seguroMedico)
                .numeroSeguro(this.numeroSeguro)
                .contactoEmergencia(this.contactoEmergencia)
                .telefonoEmergencia(this.telefonoEmergencia)
                .alergias(this.alergias)
                .antecedentesPersonales(this.antecedentesPersonales)
                .antecedentesFamiliares(this.antecedentesFamiliares)
                .clinica(clinica)
                .usuario(usuario)
                .fechaRegistro(this.fechaRegistro)
                .build();
    }
}
