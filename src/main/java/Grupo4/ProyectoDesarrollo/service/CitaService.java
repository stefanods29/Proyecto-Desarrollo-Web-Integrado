package Grupo4.ProyectoDesarrollo.service;

import java.time.LocalDateTime;
import java.util.List;

import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;

public interface CitaService {
    Cita crear(Cita cita);
    List<Cita> listar();
    Cita buscarPorId(Long id);
    void eliminar(Long id);
    List<Cita> buscarPorPaciente(Long pacienteId);
    List<Cita> buscarPorMedico(Long medicoId);
    Cita cambiarEstado(Long id, CitaEstado estado);
    List<Cita> buscarPorMedicoYFechaHoraRango(Long medicoId, LocalDateTime inicio, LocalDateTime fin);
    List<Cita> buscarPorClinicaYFechaHoraRango(Long clinicaId, LocalDateTime inicio, LocalDateTime fin);
    List<Cita> buscarCitasPorMedicoYFecha(Long medicoId, LocalDateTime inicio, LocalDateTime fin);
    long contarCitasPorClinica(Long clinicaId);
    List<Cita> buscarPorPacienteUsername(String username);
}
