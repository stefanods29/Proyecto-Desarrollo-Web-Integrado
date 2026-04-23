package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Factura;
import java.util.List;

public interface FacturaService {
    Factura crear(Factura factura);
    List<Factura> listar();
    Factura buscarPorId(Long id);
    void eliminar(Long id);
}
