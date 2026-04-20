package Grupo4.ProyectoDesarrollo.servicio;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;

import java.util.List;

public interface DetalleRecetaServicio {
    List<DetalleReceta> findAll();

    DetalleReceta findById(Long id);

    DetalleReceta save(DetalleReceta detalle);

    DetalleReceta update(Long id, DetalleReceta detalle);

    void delete(Long id);
}
