package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.repository.EspecialidadRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadServiceImplTest {

    @Mock
    private EspecialidadRepository repository;

    @InjectMocks
    private EspecialidadServiceImpl service;

    private Especialidad especialidadMock;

    @BeforeEach
    void setUp() {
        especialidadMock = new Especialidad();
        especialidadMock.setId(1L);
    }

    @Test
    void crearOk() {
        when(repository.save(any(Especialidad.class))).thenReturn(especialidadMock);

        Especialidad resultado = service.crear(new Especialidad());

        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).save(any(Especialidad.class));
    }

    @Test
    void listarOk() {
        when(repository.findAll()).thenReturn(Arrays.asList(especialidadMock, new Especialidad()));

        List<Especialidad> resultado = service.listar();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_CuandoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(especialidadMock));

        Especialidad resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_CuandoNoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorId(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void actualizar_CuandoExiste() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(Especialidad.class))).thenReturn(especialidadMock);

        Especialidad resultado = service.actualizar(1L, new Especialidad());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).save(any(Especialidad.class));
    }

    @Test
    void actualizar_CuandoNoExiste() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.actualizar(1L, new Especialidad()));
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).save(any(Especialidad.class));
    }

    @Test
    void eliminar_CuandoExiste() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void eliminar_CuandoNoExiste() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.eliminar(1L));
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).deleteById(anyLong());
    }
}