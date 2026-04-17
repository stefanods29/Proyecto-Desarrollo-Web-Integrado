package Grupo4.ProyectoDesarrollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Grupo4.ProyectoDesarrollo.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}