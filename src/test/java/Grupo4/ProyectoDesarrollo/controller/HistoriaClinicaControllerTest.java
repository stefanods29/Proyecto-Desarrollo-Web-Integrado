package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.HistoriaClinica;
import Grupo4.ProyectoDesarrollo.service.HistoriaClinicaServicio;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoriaClinicaControllerTest {

    @Mock
    private HistoriaClinicaServicio servicio;

    @InjectMocks
    private HistoriaClinicaController controller;

    private HistoriaClinica historiaMock;

    @BeforeEach
    void setUp() {
        historiaMock = new HistoriaClinica();
        historiaMock.setId(1L);
        historiaMock.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void listarOk() {
        List<HistoriaClinica> lista = Arrays.asList(historiaMock, new HistoriaClinica());
        when(servicio.findAll()).thenReturn(lista);

        ResponseEntity<List<HistoriaClinica>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void obtenerOk() {
        when(servicio.findById(1L)).thenReturn(historiaMock);

        ResponseEntity<HistoriaClinica> response = controller.obtener(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(historiaMock, response.getBody());
        verify(servicio, times(1)).findById(1L);
    }

    @Test
    void obtenerNotFound() {
        when(servicio.findById(1L)).thenReturn(null);

        ResponseEntity<HistoriaClinica> response = controller.obtener(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).findById(1L);
    }

    @Test
    void crearOk() {
        when(servicio.save(any(HistoriaClinica.class))).thenReturn(historiaMock);

        ResponseEntity<HistoriaClinica> response = controller.crear(new HistoriaClinica());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(historiaMock, response.getBody());
        verify(servicio, times(1)).save(any(HistoriaClinica.class));
    }

    @Test
    void actualizarOk() {
        when(servicio.update(eq(1L), any(HistoriaClinica.class))).thenReturn(historiaMock);

        ResponseEntity<HistoriaClinica> response = controller.actualizar(1L, new HistoriaClinica());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(historiaMock, response.getBody());
        verify(servicio, times(1)).update(eq(1L), any(HistoriaClinica.class));
    }

    @Test
    void actualizarNotFound() {
        when(servicio.update(eq(1L), any(HistoriaClinica.class))).thenReturn(null);

        ResponseEntity<HistoriaClinica> response = controller.actualizar(1L, new HistoriaClinica());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).update(eq(1L), any(HistoriaClinica.class));
    }

    @Test
    void eliminarOk() {
        doNothing().when(servicio).delete(1L);

        ResponseEntity<String> response = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Historia clínica eliminada", response.getBody());
        verify(servicio, times(1)).delete(1L);
    }
}
