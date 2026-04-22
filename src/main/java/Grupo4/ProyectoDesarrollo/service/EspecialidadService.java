package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Especialidad;

import java.util.List;

public interface EspecialidadService {

    Especialidad crear(Especialidad especialidad);

    List<Especialidad> listar();

    Especialidad buscarPorId(Long id);

    void eliminar(Long id);
}