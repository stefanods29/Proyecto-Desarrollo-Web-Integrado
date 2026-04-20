package Grupo4.ProyectoDesarrollo.servicio;

import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;

import java.util.List;

public interface HistoriaClinicaServicio {
    List<HistoriaClinica> findAll();

    HistoriaClinica findById(Long id);

    HistoriaClinica save(HistoriaClinica historia);

    HistoriaClinica update(Long id, HistoriaClinica historia);

    void delete(Long id);
}
