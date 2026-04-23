package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaServiceImplTest {

    @Mock
    private CitaRepository repository;

    @InjectMocks
    private CitaServiceImpl servicio;

    private Cita cita;

    @BeforeEach
    void setUp() {
        cita = new Cita();
        cita.setId(1L);
        
        
        
        
    }

    
    @Test
    void testCrear() {
        when(repository.save(cita)).thenReturn(cita);
        Cita resultado = servicio.crear(cita);
        assertNotNull(resultado);
        assertEquals(cita.getId(), resultado.getId());
        verify(repository, times(1)).save(cita);
    }

    @Test
    void testCrearRepositorio() {
        Cita nueva = new Cita();
        when(repository.save(nueva)).thenReturn(nueva);
        servicio.crear(nueva);
        verify(repository, times(1)).save(nueva);
    }

    
    @Test
    void testListar() {
        List<Cita> lista = Arrays.asList(cita, new Cita());
        when(repository.findAll()).thenReturn(lista);
        List<Cita> resultado = servicio.listar();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testListarVacio() {
        when(repository.findAll()).thenReturn(List.of());
        List<Cita> resultado = servicio.listar();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    
    @Test
    void testBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(cita));
        Cita resultado = servicio.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException excepcion = assertThrows(
            RuntimeException.class,
            () -> servicio.buscarPorId(99L)
        );
        assertEquals("Cita no encontrada", excepcion.getMessage());
        verify(repository, times(1)).findById(99L);
    }

    
    @Test
    void testEliminarId() {
        doNothing().when(repository).deleteById(1L);
        servicio.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(3L);
        servicio.eliminar(3L);
        verify(repository, times(1)).deleteById(3L);
        verify(repository, never()).deleteById(1L);
    }
}
