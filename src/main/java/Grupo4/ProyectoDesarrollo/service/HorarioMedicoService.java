package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import java.util.List;

public interface HorarioMedicoService {

    HorarioMedico crear(HorarioMedico horario);

    List<HorarioMedico> listar();

    HorarioMedico buscarPorId(Long id);

    void eliminar(Long id);
}