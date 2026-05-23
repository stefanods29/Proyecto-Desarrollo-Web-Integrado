package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import Grupo4.ProyectoDesarrollo.exception.DuplicateResourceException;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteServiceImpl service;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Juan");
        paciente.setNumeroDocumento("12345678");
    }

    @Test
    void testCrear() {
        when(repository.existsByNumeroDocumento("12345678")).thenReturn(false);
        when(repository.save(paciente)).thenReturn(paciente);

        Paciente result = service.crear(paciente);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(repository).save(paciente);
    }

    @Test
    void testCrearDuplicado() {
        when(repository.existsByNumeroDocumento("12345678")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.crear(paciente));
        verify(repository, never()).save(any());
    }

    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(List.of(paciente));

        List<Paciente> lista = service.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void testBuscarPorId_OK() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));

        Paciente result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void testEliminar() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));
        doNothing().when(repository).delete(paciente);

        service.eliminar(1L);

        verify(repository, times(1)).delete(paciente);
    }

    @Test
    void testBuscarPorNumeroDocumento() {
        when(repository.findByNumeroDocumento("12345678")).thenReturn(Optional.of(paciente));

        Paciente result = service.buscarPorNumeroDocumento("12345678");

        assertNotNull(result);
        assertEquals("12345678", result.getNumeroDocumento());
    }

    @Test
    void testBuscarPorNumeroDocumentoInexistente() {
        when(repository.findByNumeroDocumento("87654321")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorNumeroDocumento("87654321"));
    }

    @Test
    void testBuscarPorClinica() {
        when(repository.findByClinicaId(1L)).thenReturn(List.of(paciente));

        List<Paciente> result = service.buscarPorClinica(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}