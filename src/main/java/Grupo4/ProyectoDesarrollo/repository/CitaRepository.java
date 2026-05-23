package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPacienteId(Long pacienteId);
    List<Cita> findByMedicoId(Long medicoId);
    List<Cita> findByEstado(CitaEstado estado);
    List<Cita> findByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fin);
    List<Cita> findByClinicaIdAndFechaHoraBetween(Long clinicaId, LocalDateTime inicio, LocalDateTime fin);
    long countByEstado(CitaEstado estado);

    @Query("SELECT c FROM Cita c WHERE c.medico.id = :medicoId AND c.fechaHora >= :inicio AND c.fechaHora <= :fin")
    List<Cita> buscarCitasPorMedicoYFecha(@Param("medicoId") Long medicoId, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query("SELECT COUNT(c) FROM Cita c WHERE c.clinica.id = :clinicaId")
    long contarCitasPorClinica(@Param("clinicaId") Long clinicaId);
}
