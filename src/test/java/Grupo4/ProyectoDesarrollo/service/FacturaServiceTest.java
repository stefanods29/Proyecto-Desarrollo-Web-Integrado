package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import Grupo4.ProyectoDesarrollo.service.impl.FacturaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
        factura.setIdFactura(1L);
        factura.setNumeroFactura("F001-0001");
        factura.setSubtotal(BigDecimal.valueOf(100));
        factura.setImpuesto(BigDecimal.valueOf(18));
        factura.setTotal(BigDecimal.valueOf(118));
        factura.setEstado("PAGADO");
        factura.setMetodoPago("EFECTIVO");
    }

    @Test
    void crear() {
        when(repository.save(any(Factura.class))).thenReturn(factura);

        Factura resultado = service.crear(factura);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdFactura());
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
        assertEquals(1L, resultado.getIdFactura());
        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_NoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(1L));

        assertEquals("Factura no encontrada", ex.getMessage());
    }


    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }
}