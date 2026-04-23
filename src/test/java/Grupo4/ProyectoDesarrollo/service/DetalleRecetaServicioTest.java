package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.repository.DetalleRecetaRepository;
import Grupo4.ProyectoDesarrollo.service.impl.DetalleRecetaServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleRecetaServicioTest {

    @Mock
    private DetalleRecetaRepository repository;

    @InjectMocks
    private DetalleRecetaServicioImpl service;

    private DetalleReceta detalleMock;

    @BeforeEach
    void setUp() {
        detalleMock = new DetalleReceta();
        detalleMock.setId(1L);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(detalleMock));
        List<DetalleReceta> resultado = service.findAll();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(detalleMock));
        DetalleReceta resultado = service.findById(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void save() {
        when(repository.save(any(DetalleReceta.class))).thenReturn(detalleMock);
        DetalleReceta resultado = service.save(new DetalleReceta());
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void update() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(detalleMock));
        when(repository.save(any(DetalleReceta.class))).thenReturn(detalleMock);

        DetalleReceta resultado = service.update(1L, new DetalleReceta());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
        verify(repository).save(any(DetalleReceta.class));
    }
    @Test
    void delete() {
        when(repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(anyLong());

        service.delete(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }
}