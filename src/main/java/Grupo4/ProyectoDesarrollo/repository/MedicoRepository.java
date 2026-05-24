package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByUsuarioId(Long usuarioId);
    List<Medico> findByEspecialidadId(Long especialidadId);
    List<Medico> findByClinicaId(Long clinicaId);
    List<Medico> findByActivoTrue();

    @Query("SELECT m FROM Medico m WHERE m.clinica.id = :clinicaId AND m.especialidad.id = :especialidadId AND m.activo = true")
    List<Medico> buscarMedicosActivosPorEspecialidadYClinica(
        @Param("clinicaId") Long clinicaId, 
        @Param("especialidadId") Long especialidadId
    );
}