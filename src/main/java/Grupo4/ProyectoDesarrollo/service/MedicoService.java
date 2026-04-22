package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Medico;
import java.util.List;

public interface MedicoService {

    Medico crear(Medico medico);

    List<Medico> listar();

    Medico buscarPorId(Long id);

    void eliminar(Long id);
}