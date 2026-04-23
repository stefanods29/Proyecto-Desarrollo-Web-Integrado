package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Cita;
import java.util.List;

public interface CitaService {
    Cita crear(Cita cita);
    List<Cita> listar();
    Cita buscarPorId(Long id);
    void eliminar(Long id);
}
