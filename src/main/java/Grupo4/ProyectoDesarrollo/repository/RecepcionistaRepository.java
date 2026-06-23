package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Recepcionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecepcionistaRepository extends JpaRepository<Recepcionista, Long> {
    List<Recepcionista> findByClinicaId(Long clinicaId);
    Optional<Recepcionista> findByUsuarioUsername(String username);
    boolean existsByDni(String dni);
    boolean existsByCorreo(String correo);
}