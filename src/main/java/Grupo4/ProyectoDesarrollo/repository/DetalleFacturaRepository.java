package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
    List<DetalleFactura> findByFacturaId(Long facturaId);
    List<DetalleFactura> findByDescripcionContainingIgnoreCase(String descripcion);

    @Query("SELECT d FROM DetalleFactura d WHERE d.factura.paciente.id = :pacienteId")
    List<DetalleFactura> buscarDetallesPorPacienteId(@Param("pacienteId") Long pacienteId);

    @Query("SELECT d FROM DetalleFactura d WHERE d.factura.clinica.id = :clinicaId")
    List<DetalleFactura> buscarDetallesPorClinicaId(@Param("clinicaId") Long clinicaId);
}
