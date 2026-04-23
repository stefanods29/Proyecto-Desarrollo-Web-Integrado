package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.repository.HistoriaClinicaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoriaClinicaServicioImplTest {

    @Mock
    private HistoriaClinicaRepository repository;

    @InjectMocks
    private HistoriaClinicaServicioImpl servicio;

    private HistoriaClinica historia;

    @BeforeEach
    void setUp(){
        historia = new HistoriaClinica();
        historia.setId(1L);
    }

    @Test
    void findAll(){
        when(repository.findAll()).thenReturn(List.of(historia));

        List<HistoriaClinica> resultado = servicio.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(historia));

        HistoriaClinica resultado = servicio.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);

    }

    @Test
    void save() {
        when(repository.save(historia)).thenReturn(historia);

        HistoriaClinica resultado = servicio.save(historia);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).save(historia);
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.of(historia));
        when(repository.save(any(HistoriaClinica.class))).thenReturn(historia);

        HistoriaClinica nueva = new HistoriaClinica();

        HistoriaClinica resultado = servicio.update(1L, nueva);

        assertNotNull(resultado);
        verify(repository).findById(1L);
        verify(repository).save(any(HistoriaClinica.class));
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(1L);

        servicio.delete(1L);

        verify(repository).deleteById(1L);
    }
}