package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface FacturaService {
    Factura crear(Factura factura);
    List<Factura> listar();
    Factura buscarPorId(Long id);
    void eliminar(Long id);
    BigDecimal sumTotalFacturadoPorClinicaYEstadoYFechas(Long clinicaId, FacturaEstado estado, LocalDateTime inicio, LocalDateTime fin);
}
