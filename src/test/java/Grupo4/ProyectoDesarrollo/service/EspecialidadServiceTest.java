package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.repository.EspecialidadRepository;
import Grupo4.ProyectoDesarrollo.service.impl.EspecialidadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EspecialidadServiceTest {

    @Mock
    private EspecialidadRepository repository;

    @InjectMocks
    private EspecialidadServiceImpl service;

    private Especialidad especialidad;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        especialidad = new Especialidad();
    }

    @Test
    void crear() {
        when(repository.save(any(Especialidad.class))).thenReturn(especialidad);
        Especialidad resultado = service.crear(especialidad);
        assertNotNull(resultado);
    }

    @Test
    void listar() {
        when(repository.findAll()).thenReturn(List.of(especialidad));
        List<Especialidad> lista = service.listar();
        assertEquals(1, lista.size());
    }

    @Test
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(especialidad));
        Especialidad resultado = service.buscarPorId(1L);
        assertNotNull(resultado);
    }

    @Test
    void actualizar() {
        when(repository.findById(1L)).thenReturn(Optional.of(especialidad));
        when(repository.save(any(Especialidad.class))).thenReturn(especialidad);
        Especialidad resultado = service.actualizar(1L, especialidad);
        assertNotNull(resultado);
    }

    @Test
    void eliminar() {
        doNothing().when(repository).deleteById(1L);
        service.eliminar(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}