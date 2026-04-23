package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Paciente;
import java.util.List;

public interface PacienteService {
    Paciente crear(Paciente paciente);
    List<Paciente> listar();
    Paciente buscarPorId(Long id);
    void eliminar(Long id);
}
