package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.DetalleFactura;
import Grupo4.ProyectoDesarrollo.repository.DetalleFacturaRepository;
import Grupo4.ProyectoDesarrollo.service.impl.DetalleFacturaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalleFacturaServiceTest {

    @Mock
    private DetalleFacturaRepository repository;

    @InjectMocks
    private DetalleFacturaServiceImpl service;

    private DetalleFactura detalle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        detalle = new DetalleFactura();
    }

    @Test
    void crear() {
        when(repository.save(any(DetalleFactura.class))).thenReturn(detalle);
        DetalleFactura resultado = service.crear(detalle);
        assertNotNull(resultado);
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(detalle));
        List<DetalleFactura> lista = service.listar();
        assertEquals(1, lista.size());
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(detalle));
        DetalleFactura resultado = service.buscarPorId(1L);
        assertNotNull(resultado);
    }

    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);
        service.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}