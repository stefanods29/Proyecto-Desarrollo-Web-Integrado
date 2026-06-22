package Grupo4.ProyectoDesarrollo.service;

import java.util.List;

import Grupo4.ProyectoDesarrollo.model.Paciente;

public interface PacienteService {
    Paciente crear(Paciente paciente);
    List<Paciente> listar();
    Paciente buscarPorId(Long id);
    void eliminar(Long id);
    Paciente buscarPorNumeroDocumento(String numeroDocumento);
    List<Paciente> buscarPorClinica(Long clinicaId);
    List<Paciente> buscarPorNombreOApellido(String termino);
    Paciente buscarPorUsername(String username);
}