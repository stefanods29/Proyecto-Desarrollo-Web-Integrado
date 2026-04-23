package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        factura.setIdFactura(1L);
    }

    @Test
    void crear() {
        when(repository.save(factura)).thenReturn(factura);

        Factura resultado = service.crear(factura);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdFactura());
        verify(repository).save(factura);
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
        assertEquals(1L, resultado.getIdFactura());
    }

    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }
}