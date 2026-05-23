package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultorioRepository extends JpaRepository<Consultorio, Long> {
    List<Consultorio> findByClinicaId(Long clinicaId);

    @Query("SELECT c FROM Consultorio c WHERE c.clinica.id = :clinicaId AND LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Consultorio> buscarPorClinicaYNombre(@Param("clinicaId") Long clinicaId, @Param("nombre") String nombre);
}