package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaMedicaRepository extends JpaRepository<ConsultaMedica, Long> {
    List<ConsultaMedica> findByPacienteId(Long pacienteId);
    List<ConsultaMedica> findByMedicoId(Long medicoId);
    List<ConsultaMedica> findByHistoriaClinicaId(Long id);

    @Query("SELECT cm.medico.id, cm.medico.usuario.nombre, cm.medico.usuario.apellido, COUNT(cm) " +
           "FROM ConsultaMedica cm GROUP BY cm.medico.id, cm.medico.usuario.nombre, cm.medico.usuario.apellido " +
           "ORDER BY COUNT(cm) DESC")
    List<Object[]> obtenerRankingMedicosPorConsultas();
}
