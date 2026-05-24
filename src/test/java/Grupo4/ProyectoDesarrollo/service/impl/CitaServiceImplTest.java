package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.BusinessRuleException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
    private Medico medico;
    private Consultorio consultorio;

    @BeforeEach
    void setUp() {
        medico = new Medico();
        medico.setId(1L);

        consultorio = new Consultorio();
        consultorio.setId(1L);

        cita = new Cita();
        cita.setId(1L);
        cita.setFechaHora(LocalDateTime.now().plusDays(1));
        cita.setFechaFin(LocalDateTime.now().plusDays(1).plusHours(1));
        cita.setMedico(medico);
        cita.setConsultorio(consultorio);
        cita.setEstado(CitaEstado.PENDIENTE);
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
        nueva.setFechaHora(LocalDateTime.now().plusDays(1));
        nueva.setFechaFin(LocalDateTime.now().plusDays(1).plusHours(1));
        nueva.setMedico(medico);
        nueva.setConsultorio(consultorio);
        nueva.setEstado(CitaEstado.PENDIENTE);

        when(repository.save(nueva)).thenReturn(nueva);
        servicio.crear(nueva);
        verify(repository, times(1)).save(nueva);
    }

    @Test
    void testCrearConSolapamientoMedico() {
        when(repository.existsOverlappingCitaForMedico(any(), any(), any(), any())).thenReturn(true);

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> servicio.crear(cita));
        assertTrue(ex.getMessage().contains("médico tiene otra cita"));
    }

    @Test
    void testCrearConSolapamientoConsultorio() {
        when(repository.existsOverlappingCitaForMedico(any(), any(), any(), any())).thenReturn(false);
        when(repository.existsOverlappingCitaForConsultorio(any(), any(), any(), any())).thenReturn(true);

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> servicio.crear(cita));
        assertTrue(ex.getMessage().contains("consultorio ya está ocupado"));
    }

    @Test
    void testCrearFechasInvalidas() {
        cita.setFechaFin(cita.getFechaHora().minusHours(1));

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> servicio.crear(cita));
        assertTrue(ex.getMessage().contains("fecha de fin debe ser posterior"));
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
        ResourceNotFoundException excepcion = assertThrows(
            ResourceNotFoundException.class,
            () -> servicio.buscarPorId(99L)
        );
        assertEquals("Cita no encontrada con id: 99", excepcion.getMessage());
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void testEliminar() {
        when(repository.findById(1L)).thenReturn(Optional.of(cita));
        doNothing().when(repository).delete(cita);

        servicio.eliminar(1L);

        verify(repository, times(1)).delete(cita);
    }

    @Test
    void testCambiarEstadoValido() {
        when(repository.findById(1L)).thenReturn(Optional.of(cita));
        when(repository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = servicio.cambiarEstado(1L, CitaEstado.CONFIRMADA);

        assertEquals(CitaEstado.CONFIRMADA, resultado.getEstado());
    }

    @Test
    void testCambiarEstadoInvalido() {
        when(repository.findById(1L)).thenReturn(Optional.of(cita));

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, 
            () -> servicio.cambiarEstado(1L, CitaEstado.COMPLETADA));
        assertTrue(ex.getMessage().contains("Transición de estado no permitida"));
    }

    @Test
    void testCancelarCitaCompletada() {
        cita.setEstado(CitaEstado.COMPLETADA);
        when(repository.findById(1L)).thenReturn(Optional.of(cita));

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, 
            () -> servicio.cambiarEstado(1L, CitaEstado.CANCELADA));
        assertTrue(ex.getMessage().contains("No se puede cancelar una cita que ya ha sido completada"));
    }
}
