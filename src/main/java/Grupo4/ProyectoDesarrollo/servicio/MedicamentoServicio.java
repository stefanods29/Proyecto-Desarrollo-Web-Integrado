package Grupo4.ProyectoDesarrollo.servicio;

import Grupo4.ProyectoDesarrollo.model.Medicamento;
import java.util.List;

public interface MedicamentoServicio {
    List<Medicamento> findAll();

    Medicamento findById(Long id);

    Medicamento save(Medicamento medicamento);

    Medicamento update(Long id, Medicamento medicamento);

    void delete(Long id);
}
