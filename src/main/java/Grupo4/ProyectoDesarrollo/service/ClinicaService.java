package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import java.util.List;

public interface ClinicaService {
    Clinica crear(Clinica clinica);
    List<Clinica> listar();
    Clinica buscarPorId(Long id);
    void eliminar(Long id);
}
