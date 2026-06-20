package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ClinicaDTO;
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
        clinica.setNombre("Clinica Test");
    }

    @Test
    void testCrear() {
        when(service.crear(any(Clinica.class))).thenReturn(clinica);
        ClinicaDTO dto = ClinicaDTO.fromEntity(clinica);
        ClinicaDTO resultado = controller.crear(dto);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(service, times(1)).crear(any(Clinica.class));
    }

    @Test
    void testCrearllamaServicio() {
        Clinica nueva = new Clinica();
        when(service.crear(any(Clinica.class))).thenReturn(nueva);
        controller.crear(ClinicaDTO.fromEntity(nueva));
        verify(service, times(1)).crear(any(Clinica.class));
    }

    @Test
    void testListar() {
        List<Clinica> lista = Arrays.asList(clinica, new Clinica());
        when(service.listar()).thenReturn(lista);
        List<ClinicaDTO> resultado = controller.listar();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(service, times(1)).listar();
    }

    @Test
    void testListarVacia() {
        when(service.listar()).thenReturn(List.of());
        List<ClinicaDTO> resultado = controller.listar();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(service, times(1)).listar();
    }

    @Test
    void testBuscarPorId() {
        when(service.buscarPorId(1L)).thenReturn(clinica);
        ClinicaDTO resultado = controller.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void testBuscarPorIdInexistente() {
        when(service.buscarPorId(99L)).thenReturn(null);
        ClinicaDTO resultado = controller.buscarPorId(99L);
        assertNull(resultado);
        verify(service, times(1)).buscarPorId(99L);
    }

    @Test
    void testActualizar() {
        ClinicaDTO datosActualizados = ClinicaDTO.builder().build();
        Clinica esperada = new Clinica();
        esperada.setId(1L);
        
        when(service.crear(any(Clinica.class))).thenReturn(esperada);
        ClinicaDTO resultado = controller.actualizar(1L, datosActualizados);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId()); 
        verify(service, times(1)).crear(any(Clinica.class));
    }
}
