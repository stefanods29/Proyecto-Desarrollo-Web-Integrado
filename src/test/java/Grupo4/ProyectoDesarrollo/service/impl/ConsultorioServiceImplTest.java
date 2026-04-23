package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.repository.ConsultorioRepository;
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
class ConsultorioServiceImplTest {

    @Mock
    private ConsultorioRepository repository;

    @InjectMocks
    private ConsultorioServiceImpl service;

    private Consultorio consultorioMock;

    @BeforeEach
    void setUp() {
        consultorioMock = new Consultorio();
        consultorioMock.setId(1L);
    }

    @Test
    void crearOk() {
        when(repository.save(any(Consultorio.class))).thenReturn(consultorioMock);

        Consultorio resultado = service.crear(new Consultorio());

        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).save(any(Consultorio.class));
    }

    @Test
    void listarok() {
        when(repository.findAll()).thenReturn(Arrays.asList(consultorioMock, new Consultorio()));

        List<Consultorio> resultado = service.listar();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_CuandoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(consultorioMock));

        Consultorio resultado = service.buscarPorId(1L);

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
        when(repository.save(any(Consultorio.class))).thenReturn(consultorioMock);

        Consultorio resultado = service.actualizar(1L, new Consultorio());

        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).save(any(Consultorio.class));
    }

    @Test
    void actualizar_CuandoNoExiste() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.actualizar(1L, new Consultorio()));
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).save(any(Consultorio.class));
    }

    @Test
    void eliminar_CuandoExisteOk() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void eliminar_CuandoNoExisteOk() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.eliminar(1L));
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).deleteById(anyLong());
    }
}