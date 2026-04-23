package Grupo4.ProyectoDesarrollo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.repository.MedicoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class MedicoServiceImplTest {

    @Mock
    private MedicoRepository repository;

    @InjectMocks
    private MedicoServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        Medico medico = new Medico();
        medico.setId(1L);

        when(repository.save(medico)).thenReturn(medico);

        Medico result = service.crear(medico);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(List.of(new Medico()));

        List<Medico> lista = service.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId_OK() {
        Medico medico = new Medico();
        medico.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(medico));

        Medico result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Medico result = service.buscarPorId(1L);

        assertNull(result); 
    }

    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
    
}