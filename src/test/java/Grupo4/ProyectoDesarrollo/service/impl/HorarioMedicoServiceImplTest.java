package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.repository.HorarioMedicoRepository;

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
class HorarioMedicoServiceImplTest {

    @Mock
    private HorarioMedicoRepository repository;

    @InjectMocks
    private HorarioMedicoServiceImpl servicio;

    private HorarioMedico horario;

    @BeforeEach
    void setUp() {
        horario = new HorarioMedico();
        horario.setId(1L);
    }

    @Test
    void crear() {
        when(repository.save(horario)).thenReturn(horario);

        HorarioMedico resultado = servicio.crear(horario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).save(horario);
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(horario));

        List<HorarioMedico> resultado = servicio.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(horario));

        HorarioMedico resultado = servicio.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
    }
    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);

        servicio.eliminar(1L);

        verify(repository).deleteById(1L);
    }
}


