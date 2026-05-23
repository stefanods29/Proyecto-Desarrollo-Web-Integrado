package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import java.util.List;

public interface CitaService {
    Cita crear(Cita cita);
    List<Cita> listar();
    Cita buscarPorId(Long id);
    void eliminar(Long id);
    List<Cita> buscarPorPaciente(Long pacienteId);
    List<Cita> buscarPorMedico(Long medicoId);
    Cita cambiarEstado(Long id, CitaEstado estado);
}
