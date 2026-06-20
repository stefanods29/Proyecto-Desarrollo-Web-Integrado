package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.MetodoPago;
import Grupo4.ProyectoDesarrollo.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacturaServiceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private FacturaService facturaService;

    @Test
    void calculaTotalesAlPersistirFactura() {
        Clinica clinica = crearClinica("F");
        Paciente paciente = crearPaciente(clinica, "22222222");

        Factura factura = new Factura();
        factura.setEstado(FacturaEstado.PENDIENTE);
        factura.setMetodoPago(MetodoPago.EFECTIVO);
        factura.setPaciente(paciente);
        factura.setClinica(clinica);

        DetalleFactura detalle = new DetalleFactura();
        detalle.setDescripcion("Consulta");
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(BigDecimal.valueOf(50.00));

        factura.setDetalles(List.of(detalle));

        Factura guardada = facturaService.crear(factura);

        assertNotNull(guardada.getId());
        assertNotNull(guardada.getNumeroFactura());
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(guardada.getSubtotal()));
        assertEquals(0, BigDecimal.valueOf(18.00).compareTo(guardada.getImpuesto()));
        assertEquals(0, BigDecimal.valueOf(118.00).compareTo(guardada.getTotal()));
    }
}
