package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import java.util.List;

public interface DetalleFacturaService {
    DetalleFactura crear(DetalleFactura detalleFactura);
    List<DetalleFactura> listar();
    DetalleFactura buscarPorId(Long id);
    void eliminar(Long id);
}
