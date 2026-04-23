package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
