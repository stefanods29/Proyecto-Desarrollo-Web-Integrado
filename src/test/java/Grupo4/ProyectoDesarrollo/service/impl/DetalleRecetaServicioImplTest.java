package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.repository.DetalleRecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleRecetaServicioImplTest {

    @Mock
    private DetalleRecetaRepository repository;

    @InjectMocks
    private DetalleRecetaServicioImpl service;

    private DetalleReceta detalleMock;

    @BeforeEach
    void setUp() {
        detalleMock = new DetalleReceta();
        detalleMock.setId(1L);
        detalleMock.setDosis("500mg");
    }

    @Test
    void findAll_DeberiaRetornarListaDeDetalles() {
        when(repository.findAll()).thenReturn(Arrays.asList(detalleMock, new DetalleReceta()));

        List<DetalleReceta> resultado = service.findAll();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_DeberiaRetornarDetalle_CuandoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(detalleMock));

        DetalleReceta resultado = service.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_DeberiaLanzarExcepcion_CuandoNoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void save_DeberiaGuardarYRetornarDetalle() {
        when(repository.save(any(DetalleReceta.class))).thenReturn(detalleMock);

        DetalleReceta resultado = service.save(new DetalleReceta());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).save(any(DetalleReceta.class));
    }

    @Test
    void update_DeberiaActualizarYRetornarDetalle_CuandoExiste() {
        DetalleReceta datosNuevos = new DetalleReceta();
        datosNuevos.setDosis("1g");

        when(repository.findById(1L)).thenReturn(Optional.of(detalleMock));
        when(repository.save(any(DetalleReceta.class))).thenReturn(detalleMock);

        DetalleReceta resultado = service.update(1L, datosNuevos);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(DetalleReceta.class));
    }

    @Test
    void update_DeberiaLanzarExcepcion_CuandoNoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.update(1L, new DetalleReceta()));
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(DetalleReceta.class));
    }

    @Test
    void delete_DeberiaEliminar_CuandoExiste() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void delete_DeberiaLanzarExcepcion_CuandoNoExiste() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.delete(1L));
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).deleteById(anyLong());
    }
}