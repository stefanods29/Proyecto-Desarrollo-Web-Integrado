package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByPacienteId(Long pacienteId);
    List<Factura> findByEstado(FacturaEstado estado);
    Optional<Factura> findFirstByOrderByIdDesc();

    @Query("SELECT COALESCE(SUM(f.total), 0) FROM Factura f WHERE f.clinica.id = :clinicaId AND f.estado = :estado AND f.fechaEmision BETWEEN :inicio AND :fin")
    BigDecimal sumTotalFacturadoPorClinicaYEstadoYFechas(
        @Param("clinicaId") Long clinicaId, 
        @Param("estado") FacturaEstado estado, 
        @Param("inicio") LocalDateTime inicio, 
        @Param("fin") LocalDateTime fin
    );

    @Query("SELECT COALESCE(SUM(f.total), 0) FROM Factura f WHERE f.estado = :estado AND f.fechaEmision BETWEEN :inicio AND :fin")
    BigDecimal sumTotalFacturadoPorEstadoYFechas(
        @Param("estado") FacturaEstado estado, 
        @Param("inicio") LocalDateTime inicio, 
        @Param("fin") LocalDateTime fin
    );
}
