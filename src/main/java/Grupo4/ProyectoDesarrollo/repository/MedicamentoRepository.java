package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.Medicamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findByActivoTrue();
    Optional<Medicamento> findByNombreComercialIgnoreCase(String nombreComercial);
    List<Medicamento> findByNombreComercialContainingIgnoreCaseOrNombreGenericoContainingIgnoreCase(String nombreComercial, String nombreGenerico);
    List<Medicamento> findByViaAdministracion(String viaAdministracion);

    @Query("SELECT m FROM Medicamento m WHERE m.activo = true AND LOWER(m.nombreComercial) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Medicamento> buscarActivosPorNombreComercial(@Param("term") String term);
}
