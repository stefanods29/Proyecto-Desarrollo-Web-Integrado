package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.repository.RecetaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class RecetaServicioImplTest {

    @Mock
    private RecetaRepository repository;

    @InjectMocks
    private RecetaServicioImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(new Receta()));

        List<Receta> lista = service.findAll();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testFindById() {
        Receta receta = new Receta();
        receta.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(receta));

        Receta result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSave() {
        Receta receta = new Receta();

        when(repository.save(receta)).thenReturn(receta);

        Receta result = service.save(receta);

        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        Receta existente = new Receta();
        existente.setId(1L);

        Receta nueva = new Receta();
        nueva.setIndicaciones("Tomar cada 8h");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Receta.class))).thenReturn(existente);

        Receta actualizado = service.update(1L, nueva);

        assertNotNull(actualizado);
    }

    @Test
    void testUpdateNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Receta result = service.update(1L, new Receta());

        assertNull(result);
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}