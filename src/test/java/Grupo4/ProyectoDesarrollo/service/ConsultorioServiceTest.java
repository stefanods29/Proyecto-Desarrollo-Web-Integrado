package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.repository.ConsultorioRepository;
import Grupo4.ProyectoDesarrollo.service.impl.ConsultorioServiceImpl;
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
class ConsultorioServiceTest {

    @Mock
    private ConsultorioRepository repository;

    @InjectMocks
    private ConsultorioServiceImpl service;

    private Consultorio consultorio;

    @BeforeEach
    void setUp() {
        consultorio = new Consultorio();
        consultorio.setId(1L);
    }

    @Test
    void crear() {
        when(repository.save(any(Consultorio.class))).thenReturn(consultorio);
        Consultorio resultado = service.crear(consultorio);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(consultorio));
        List<Consultorio> lista = service.listar();
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(consultorio));
        Consultorio resultado = service.buscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void actualizar() {
        // Usamos anyLong() para evitar cualquier error de coincidencia de ID
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any(Consultorio.class))).thenReturn(consultorio);

        Consultorio resultado = service.actualizar(1L, consultorio);

        assertNotNull(resultado);
        verify(repository).existsById(1L);
        verify(repository).save(any(Consultorio.class));
    }

    @Test
    void eliminar() {
        when(repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(anyLong());

        service.eliminar(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }
}