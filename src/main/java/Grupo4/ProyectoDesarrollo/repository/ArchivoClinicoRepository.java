package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoClinicoRepository extends JpaRepository<ArchivoClinico, Long> {
    List<ArchivoClinico> findByConsultaMedicaId(Long consultaMedicaId);

    @Query("SELECT a FROM ArchivoClinico a WHERE a.consultaMedica.paciente.id = :pacienteId")
    List<ArchivoClinico> buscarArchivosPorPacienteId(@Param("pacienteId") Long pacienteId);
}
