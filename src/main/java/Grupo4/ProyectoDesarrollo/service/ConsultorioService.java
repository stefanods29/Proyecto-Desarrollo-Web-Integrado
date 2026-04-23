package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Consultorio;
import java.util.List;

public interface ConsultorioService {

    Consultorio crear(Consultorio consultorio);

    List<Consultorio> listar();

    Consultorio buscarPorId(Long id);

    Consultorio actualizar(Long id, Consultorio consultorio);

    void eliminar(Long id);
}