package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;

import java.util.List;


public interface ArchivoClinicoServicio {

    List<ArchivoClinico> findAll();

    ArchivoClinico findById(Long id);

    ArchivoClinico save(ArchivoClinico archivo);

    ArchivoClinico update(Long id, ArchivoClinico archivo);

    void delete(Long id);
}
