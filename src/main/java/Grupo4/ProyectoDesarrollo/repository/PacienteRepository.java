package Grupo4.ProyectoDesarrollo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Grupo4.ProyectoDesarrollo.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
    boolean existsByNumeroDocumento(String numeroDocumento);
    List<Paciente> findByClinicaId(Long clinicaId);

    @Query("SELECT p FROM Paciente p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(p.apellido) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Paciente> buscarPorNombreOApellido(@Param("term") String term);
    
    Optional<Paciente> findByUsuarioUsername(String username);
}