package Grupo4.ProyectoDesarrollo.servicio;

import Grupo4.ProyectoDesarrollo.model.Receta;

import java.util.List;

public interface RecetaServicio {
    List<Receta> findAll();

    Receta findById(Long id);

    Receta save(Receta receta);

    Receta update(Long id, Receta receta);

    void delete(Long id);
}
