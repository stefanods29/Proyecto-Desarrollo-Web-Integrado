package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.repository.ClinicaRepository;

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
public class ClinicaServiceImplTest {

    @Mock
    private ClinicaRepository repository;

    @InjectMocks
    private ClinicaServiceImpl servicio;

    private Clinica clinica;

    @BeforeEach
    void setUp() {
        clinica = new Clinica();
        clinica.setId(1L);
        // clinica.setNombre("Clínica San Juan");
        // clinica.setDireccion("Av. Principal 123");
        // clinica.setTelefono("999888777");
    }

    // crear
    @Test
    void testCrear() {
        when(repository.save(clinica)).thenReturn(clinica);
        Clinica resultado = servicio.crear(clinica);
        assertNotNull(resultado);
        assertEquals(clinica.getId(), resultado.getId());
        verify(repository, times(1)).save(clinica);
    }

    @Test
    void testCrearllamaRepositorio() {
        Clinica nueva = new Clinica();
        when(repository.save(nueva)).thenReturn(nueva);
        servicio.crear(nueva);
        verify(repository, times(1)).save(nueva);
    }

    // listar()
    @Test
    void testListar() {
        List<Clinica> lista = Arrays.asList(clinica, new Clinica());
        when(repository.findAll()).thenReturn(lista);
        List<Clinica> resultado = servicio.listar();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testListar_retornaListaVacia() {
        when(repository.findAll()).thenReturn(List.of());
        List<Clinica> resultado = servicio.listar();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    // buscarPorId
    @Test
    void testBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(clinica));
        Clinica resultado = servicio.buscarPorId(1L);
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
        assertEquals("Clinica no encontrada", excepcion.getMessage());
        verify(repository, times(1)).findById(99L);
    }

    // eliminar
    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(1L);
        servicio.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarnoLlamaId() {
        doNothing().when(repository).deleteById(3L);
        servicio.eliminar(3L);
        verify(repository, times(1)).deleteById(3L);
        verify(repository, never()).deleteById(1L);
    }
}
