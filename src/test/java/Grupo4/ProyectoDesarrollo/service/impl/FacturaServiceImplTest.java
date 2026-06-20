package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.BusinessRuleException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceImplTest {

    @Mock
    private FacturaRepository repository;

    @InjectMocks
    private FacturaServiceImpl service;

    private Factura factura;

    @BeforeEach
    void setUp() {
        factura = new Factura();
        factura.setId(1L);
        factura.setNumeroFactura("F001-00000001");
        factura.setEstado(FacturaEstado.PENDIENTE);
        
        List<DetalleFactura> detalles = new ArrayList<>();
        DetalleFactura det = new DetalleFactura();
        det.setId(1L);
        det.setDescripcion("Consulta General");
        det.setCantidad(2);
        det.setPrecioUnitario(BigDecimal.valueOf(50.00));
        detalles.add(det);
        
        factura.setDetalles(detalles);
    }

    @Test
    void crear() {
        when(repository.save(any(Factura.class))).thenReturn(factura);

        Factura resultado = service.crear(factura);

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(100.00), resultado.getSubtotal());
        assertEquals(BigDecimal.valueOf(18.00).setScale(2), resultado.getImpuesto());
        assertEquals(BigDecimal.valueOf(118.00).setScale(2), resultado.getTotal());
        verify(repository).save(factura);
    }

    @Test
    void crearNuevaGeneraNumeroSecuencial() {
        Factura nueva = new Factura();
        nueva.setEstado(FacturaEstado.PENDIENTE);
        
        when(repository.findFirstByOrderByIdDesc()).thenReturn(Optional.of(factura));
        when(repository.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Factura guardada = service.crear(nueva);

        assertNotNull(guardada.getNumeroFactura());
        assertEquals("F001-00000002", guardada.getNumeroFactura());
    }

    @Test
    void testModificarFacturaPagadaLanzaExcepcion() {
        factura.setEstado(FacturaEstado.PAGADA);
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(factura));

        assertThrows(BusinessRuleException.class, () -> service.crear(factura));
        verify(repository, never()).save(any());
    }

    @Test
    void testRegistrarFechaPagoAlTransicionarAPagada() {
        Factura existente = new Factura();
        existente.setId(1L);
        existente.setEstado(FacturaEstado.PENDIENTE);
        existente.setNumeroFactura("F001-00000001");

        Factura modificada = new Factura();
        modificada.setId(1L);
        modificada.setEstado(FacturaEstado.PAGADA);
        modificada.setNumeroFactura("F001-00000001");

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Factura resultado = service.crear(modificada);

        assertNotNull(resultado.getFechaPago());
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(factura));

        List<Factura> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(factura));

        Factura resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void eliminar() {
        when(repository.findById(1L)).thenReturn(Optional.of(factura));
        doNothing().when(repository).delete(factura);

        service.eliminar(1L);

        verify(repository).delete(factura);
    }

    @Test
    void eliminarFacturaPagadaLanzaExcepcion() {
        factura.setEstado(FacturaEstado.PAGADA);
        when(repository.findById(1L)).thenReturn(Optional.of(factura));

        assertThrows(BusinessRuleException.class, () -> service.eliminar(1L));
        verify(repository, never()).delete(any());
    }
}