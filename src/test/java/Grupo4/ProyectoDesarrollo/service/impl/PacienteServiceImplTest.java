package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class PacienteServiceImplTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");

        when(repository.save(paciente)).thenReturn(paciente);

        Paciente result = service.crear(paciente);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(List.of(new Paciente()));

        List<Paciente> lista = service.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId_OK() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(paciente));

        Paciente result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(1L);
        });

        assertEquals("Paciente no encontrado", exception.getMessage());
    }

    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}