package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.MetodoPago;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import Grupo4.ProyectoDesarrollo.service.impl.FacturaServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepository repository;

    @InjectMocks
    private FacturaServiceImpl service;

    private Factura factura;

    @BeforeEach
    void setUp() {
        factura = new Factura();
        factura.setId(1L);
        factura.setNumeroFactura("F001-0001");
        factura.setSubtotal(BigDecimal.valueOf(100));
        factura.setImpuesto(BigDecimal.valueOf(18));
        factura.setTotal(BigDecimal.valueOf(118));
        factura.setEstado(FacturaEstado.PENDIENTE);
        factura.setMetodoPago(MetodoPago.EFECTIVO);
        factura.setDetalles(new ArrayList<>());
    }

    @Test
    void crear() {
        when(repository.findById(1L)).thenReturn(Optional.of(factura));
        when(repository.save(any(Factura.class))).thenReturn(factura);

        Factura resultado = service.crear(factura);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).save(any(Factura.class));
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(factura));

        List<Factura> resultado = service.listar();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(factura));

        Factura resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_NoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId(1L));
    }

    @Test
    void eliminar() {
        when(repository.findById(1L)).thenReturn(Optional.of(factura));
        doNothing().when(repository).delete(factura);

        service.eliminar(1L);

        verify(repository).delete(factura);
    }
}