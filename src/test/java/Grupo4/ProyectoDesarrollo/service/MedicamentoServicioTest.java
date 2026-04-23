package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.repository.MedicamentoRepository;
import Grupo4.ProyectoDesarrollo.service.impl.MedicamentoServicioImpl;
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
class MedicamentoServicioTest {

    @Mock
    private MedicamentoRepository repository;

    @InjectMocks
    private MedicamentoServicioImpl service;

    private Medicamento medicamento;

    @BeforeEach
    void setUp() {
        medicamento = new Medicamento();
        medicamento.setId(1L);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(medicamento));

        List<Medicamento> resultado = service.findAll();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(medicamento));

        Medicamento resultado = service.findById(1L);

        assertNotNull(resultado);
        verify(repository).findById(1L);
    }

    @Test
    void save() {
        when(repository.save(any(Medicamento.class))).thenReturn(medicamento);

        Medicamento resultado = service.save(medicamento);

        assertNotNull(resultado);
        verify(repository).save(any(Medicamento.class));
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(repository.save(any(Medicamento.class))).thenReturn(medicamento);

        Medicamento resultado = service.update(1L, new Medicamento());

        assertNotNull(resultado);
        verify(repository).findById(1L);
        verify(repository).save(any(Medicamento.class));
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}