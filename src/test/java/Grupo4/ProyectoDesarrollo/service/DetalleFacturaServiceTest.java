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
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        service.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorFacturaId() {
        when(repository.findByFacturaId(1L)).thenReturn(List.of(detalle));
        List<DetalleFactura> resultado = service.buscarPorFacturaId(1L);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByFacturaId(1L);
    }

    @Test
    void buscarPorDescripcion() {
        when(repository.findByDescripcionContainingIgnoreCase("Consulta")).thenReturn(List.of(detalle));
        List<DetalleFactura> resultado = service.buscarPorDescripcion("Consulta");
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByDescripcionContainingIgnoreCase("Consulta");
    }

    @Test
    void buscarPorClinicaId() {
        when(repository.buscarDetallesPorClinicaId(1L)).thenReturn(List.of(detalle));
        List<DetalleFactura> resultado = service.buscarPorClinicaId(1L);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).buscarDetallesPorClinicaId(1L);
    }
}