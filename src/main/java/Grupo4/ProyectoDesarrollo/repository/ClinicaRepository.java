package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.enums.ClinicaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    Optional<Clinica> findByRuc(String ruc);
    Optional<Clinica> findByCorreo(String correo);

    @Query("SELECT c FROM Clinica c WHERE c.estado = :estado")
    List<Clinica> buscarClinicasPorEstado(@Param("estado") ClinicaEstado estado);
}
