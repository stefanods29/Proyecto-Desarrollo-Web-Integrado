package Grupo4.ProyectoDesarrollo.repository;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
    List<DetalleReceta> findByRecetaId(Long recetaId);
    List<DetalleReceta> findByMedicamentoId(Long medicamentoId);

    @Query("SELECT d FROM DetalleReceta d WHERE d.receta.paciente.id = :pacienteId")
    List<DetalleReceta> buscarDetallesPorPacienteId(@Param("pacienteId") Long pacienteId);

    @Query("SELECT d FROM DetalleReceta d WHERE d.medicamento.activo = true AND d.receta.paciente.id = :pacienteId")
    List<DetalleReceta> buscarDetallesMedicamentoActivoPorPacienteId(@Param("pacienteId") Long pacienteId);
}
