package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.repository.ConsultaMedicaRepository;
import Grupo4.ProyectoDesarrollo.service.impl.ConsultaMedicaServicioImpl;
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
class ConsultaMedicaServicioTest {

    @Mock
    private ConsultaMedicaRepository repository;

    @InjectMocks
    private ConsultaMedicaServicioImpl service;

    private ConsultaMedica consultaMock;

    @BeforeEach
    void setUp() {
        consultaMock = new ConsultaMedica();
        consultaMock.setId(1L);
    }

    @Test
    void findAllOk() {
        when(repository.findAll()).thenReturn(Arrays.asList(consultaMock));

        List<ConsultaMedica> resultado = service.findAll();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findByIdOk() {
        when(repository.findById(1L)).thenReturn(Optional.of(consultaMock));

        ConsultaMedica resultado = service.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void saveOk() {
        when(repository.save(any(ConsultaMedica.class))).thenReturn(consultaMock);

        ConsultaMedica resultado = service.save(new ConsultaMedica());

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(ConsultaMedica.class));
    }

    @Test
    void updateOk() {
        when(repository.findById(1L)).thenReturn(Optional.of(consultaMock));
        when(repository.save(any(ConsultaMedica.class))).thenReturn(consultaMock);

        ConsultaMedica resultado = service.update(1L, new ConsultaMedica());

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(ConsultaMedica.class));
    }

    @Test
    void deleteOk() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}