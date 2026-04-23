package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import java.util.List;

public interface EspecialidadService {

    Especialidad crear(Especialidad especialidad);

    List<Especialidad> listar();

    Especialidad buscarPorId(Long id);

    Especialidad actualizar(Long id, Especialidad especialidad);

    void eliminar(Long id);
}