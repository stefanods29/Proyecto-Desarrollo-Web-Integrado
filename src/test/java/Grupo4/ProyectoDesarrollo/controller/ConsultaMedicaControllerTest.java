package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.service.ConsultaMedicaServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaMedicaControllerTest {
    @Mock
    private ConsultaMedicaServicio servicio;

    @InjectMocks
    private ConsultaMedicaController controller;

    private ConsultaMedica consultaMock;

    @BeforeEach
    void setUp() {
        consultaMock = new ConsultaMedica();
    }

    @Test
    void listarOk() {
        List<ConsultaMedica> listaConsultas = Arrays.asList(consultaMock, new ConsultaMedica());
        when(servicio.findAll()).thenReturn(listaConsultas);

        ResponseEntity<List<ConsultaMedica>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void obtenerOk() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(consultaMock);

        ResponseEntity<ConsultaMedica> response = controller.obtener(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consultaMock, response.getBody());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void obtenerok() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(null);

        ResponseEntity<ConsultaMedica> response = controller.obtener(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void crearOk() {
        when(servicio.save(any(ConsultaMedica.class))).thenReturn(consultaMock);

        ResponseEntity<ConsultaMedica> response = controller.crear(new ConsultaMedica());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consultaMock, response.getBody());
        verify(servicio, times(1)).save(any(ConsultaMedica.class));
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        when(servicio.update(eq(id), any(ConsultaMedica.class))).thenReturn(consultaMock);

        ResponseEntity<ConsultaMedica> response = controller.actualizar(id, new ConsultaMedica());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consultaMock, response.getBody());
        verify(servicio, times(1)).update(eq(id), any(ConsultaMedica.class));
    }

    @Test
    void actualizarok() {
        Long id = 1L;
        when(servicio.update(eq(id), any(ConsultaMedica.class))).thenReturn(null);

        ResponseEntity<ConsultaMedica> response = controller.actualizar(id, new ConsultaMedica());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).update(eq(id), any(ConsultaMedica.class));
    }

    @Test
    void eliminarOk() {
        Long id = 1L;
        doNothing().when(servicio).delete(id);

        ResponseEntity<String> response = controller.eliminar(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Consulta eliminada", response.getBody());
        verify(servicio, times(1)).delete(id);
    }
}