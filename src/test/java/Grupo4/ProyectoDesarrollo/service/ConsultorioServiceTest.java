package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Consultorio;
import Grupo4.ProyectoDesarrollo.repository.ConsultorioRepository;
import Grupo4.ProyectoDesarrollo.service.impl.ConsultorioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultorioServiceTest {

    @Mock
    private ConsultorioRepository repository;

    @InjectMocks
    private ConsultorioServiceImpl service;

    private Consultorio consultorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        when(repository.findById(1L)).thenReturn(Optional.of(consultorio));
        when(repository.save(any(Consultorio.class))).thenReturn(consultorio);
        Consultorio resultado = service.actualizar(1L, consultorio);
        assertNotNull(resultado);
    }

    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);
        service.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}