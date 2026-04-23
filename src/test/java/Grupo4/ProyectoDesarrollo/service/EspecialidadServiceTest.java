package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.repository.EspecialidadRepository;
import Grupo4.ProyectoDesarrollo.service.impl.EspecialidadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadServiceTest {

    @Mock
    private EspecialidadRepository repository;

    @InjectMocks
    private EspecialidadServiceImpl service;

    private Especialidad especialidad;

    @BeforeEach
    void setUp() {
        especialidad = new Especialidad();
        especialidad.setId(1L);
    }

    @Test
    void crear() {
        when(repository.save(any(Especialidad.class))).thenReturn(especialidad);
        Especialidad resultado = service.crear(especialidad);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(especialidad));
        List<Especialidad> lista = service.listar();
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(especialidad));
        Especialidad resultado = service.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void actualizar() {
        // Obligatorio simular que existe para que no lance la RuntimeException
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any(Especialidad.class))).thenReturn(especialidad);

        Especialidad resultado = service.actualizar(1L, especialidad);

        assertNotNull(resultado);
        verify(repository).existsById(1L);
        verify(repository).save(any(Especialidad.class));
    }

    @Test
    void eliminar() {
        // Obligatorio simular que existe para que no lance la RuntimeException
        when(repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(anyLong());

        service.eliminar(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }
}