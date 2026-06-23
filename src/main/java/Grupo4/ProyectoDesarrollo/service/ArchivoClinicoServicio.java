package Grupo4.ProyectoDesarrollo.service;

import java.util.List;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;


public interface ArchivoClinicoServicio {
    List<ArchivoClinico> findAll();
    ArchivoClinico findById(Long id);
    ArchivoClinico save(ArchivoClinico archivo);
    ArchivoClinico update(Long id, ArchivoClinico archivo);
    void delete(Long id);
    List<ArchivoClinico> buscarPorPacienteUsername(String username);
}
