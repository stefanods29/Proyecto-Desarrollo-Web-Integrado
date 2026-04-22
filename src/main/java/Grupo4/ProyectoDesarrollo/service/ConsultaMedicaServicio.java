package Grupo4.ProyectoDesarrollo.service;


import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;

import java.util.List;

public interface ConsultaMedicaServicio {
    List<ConsultaMedica> findAll();

    ConsultaMedica findById(Long id);

    ConsultaMedica save(ConsultaMedica consulta);

    ConsultaMedica update(Long id, ConsultaMedica consulta);

    void delete(Long id);
}
