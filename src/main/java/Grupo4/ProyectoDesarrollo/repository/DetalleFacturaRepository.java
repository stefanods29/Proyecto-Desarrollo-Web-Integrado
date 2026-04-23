package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
}
