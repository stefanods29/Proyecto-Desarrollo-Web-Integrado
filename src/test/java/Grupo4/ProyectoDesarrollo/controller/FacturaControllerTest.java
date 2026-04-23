package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Factura;
import Grupo4.ProyectoDesarrollo.service.FacturaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaControllerTest {

    @Mock
    private FacturaService service;

    @InjectMocks
    private FacturaController controller;

    private Factura facturaMock;

    @BeforeEach
    void setUp() {
        facturaMock = new Factura();
        facturaMock.setIdFactura(1L);
        facturaMock.setNumeroFactura("F001");
    }

    @Test
    void crearOk(){
        when(service.crear(any(Factura.class))).thenReturn(facturaMock);

        Factura response = controller.crear(new Factura());

        assertNotNull(response);
        assertEquals("F001", response.getNumeroFactura());
        verify(service, times(1)).crear(any(Factura.class));
    }

    @Test
    void listarOk(){
        List<Factura> lista = Arrays.asList(facturaMock, new Factura());
        when(service.listar()).thenReturn(lista);

        List<Factura> response = controller.listar();

        assertEquals(2, response.size());
        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorIdOk(){
        when(service.buscarPorId(1L)).thenReturn(facturaMock);

        Factura response = controller.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getIdFactura());
        verify(service, times(1)).buscarPorId(1L);
    }
    @Test
    void actualizarOk(){
        when(service.crear(any(Factura.class))).thenReturn(facturaMock);

        Factura response = controller.actualizar(1L, new Factura());

        assertNotNull(response);
        verify(service, times(1)).crear(any(Factura.class));
    }

}