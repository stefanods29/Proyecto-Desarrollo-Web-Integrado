package Grupo4.ProyectoDesarrollo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;

@Repository
public interface ArchivoClinicoRepository extends JpaRepository<ArchivoClinico, Long> {
    List<ArchivoClinico> findByConsultaMedicaId(Long consultaMedicaId);

    @Query("SELECT a FROM ArchivoClinico a WHERE a.consultaMedica.paciente.id = :pacienteId")
    List<ArchivoClinico> buscarArchivosPorPacienteId(@Param("pacienteId") Long pacienteId);

    List<ArchivoClinico> findByConsultaMedicaPacienteUsuarioUsername(String username);
}
