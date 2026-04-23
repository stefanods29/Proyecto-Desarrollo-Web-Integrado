package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.repository.HorarioMedicoRepository;
import Grupo4.ProyectoDesarrollo.service.impl.HorarioMedicoServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioMedicoServiceTest {

    @Mock
    private HorarioMedicoRepository repository;

    @InjectMocks
    private HorarioMedicoServiceImpl service;

    private HorarioMedico horario;

    @BeforeEach
    void setUp() {
        horario = new HorarioMedico();
        horario.setId(1L);
    }

    @Test
    void crear() {
        when(repository.save(any(HorarioMedico.class))).thenReturn(horario);

        HorarioMedico resultado = service.crear(horario);

        assertNotNull(resultado);
        verify(repository).save(any(HorarioMedico.class));
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(horario));

        List<HorarioMedico> resultado = service.listar();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(horario));

        HorarioMedico resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        verify(repository).findById(1L);
    }

    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }
}