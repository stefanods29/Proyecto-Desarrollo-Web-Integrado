package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;

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
public class ClinicaControllerTest {
    @Mock
    private ClinicaService service;
    @InjectMocks
    private ClinicaController controller;
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
        when(service.crear(clinica)).thenReturn(clinica);
        Clinica resultado = controller.crear(clinica);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(service, times(1)).crear(clinica);
    }

    @Test
    void testCrearllamaServicio() {
        Clinica nueva = new Clinica();
        when(service.crear(nueva)).thenReturn(nueva);
        controller.crear(nueva);
        verify(service, times(1)).crear(nueva);
    }

    // listar
    @Test
    void testListar() {
        List<Clinica> lista = Arrays.asList(clinica, new Clinica());
        when(service.listar()).thenReturn(lista);
        List<Clinica> resultado = controller.listar();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(service, times(1)).listar();
    }

    @Test
    void testListarVacia() {
        when(service.listar()).thenReturn(List.of());
        List<Clinica> resultado = controller.listar();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(service, times(1)).listar();
    }

    // buscarPorId
    @Test
    void testBuscarPorId() {
        when(service.buscarPorId(1L)).thenReturn(clinica);
        Clinica resultado = controller.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void testBuscarPorIdInexistente() {
        when(service.buscarPorId(99L)).thenReturn(null);
        Clinica resultado = controller.buscarPorId(99L);
        assertNull(resultado);
        verify(service, times(1)).buscarPorId(99L);
    }

    // actualizar
    @Test
    void testActualizar() {
        Clinica datosActualizados = new Clinica();
        // datosActualizados.setNombre("Clínica Actualizada");
        // datosActualizados.setDireccion("Av. Nueva 456");
        Clinica esperada = new Clinica();
        esperada.setId(1L);
        // esperada.setNombre("Clínica Actualizada");
        when(service.crear(any(Clinica.class))).thenReturn(esperada);
        Clinica resultado = controller.actualizar(1L, datosActualizados);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId()); // El ID fue seteado por el controller antes de llamar al service
        verify(service, times(1)).crear(datosActualizados);
    }

    @Test
    void testActualizarsetIdObjetoRecibido() {
        Clinica datosActualizados = new Clinica();
        when(service.crear(datosActualizados)).thenReturn(datosActualizados);
        controller.actualizar(5L, datosActualizados);
        assertEquals(5L, datosActualizados.getId()); // El controller hace clinica.setId(id)
        verify(service, times(1)).crear(datosActualizados);
    }
}