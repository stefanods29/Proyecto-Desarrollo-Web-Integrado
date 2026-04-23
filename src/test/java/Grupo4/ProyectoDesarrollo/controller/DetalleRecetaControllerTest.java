package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.service.DetalleRecetaServicio;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleRecetaControllerTest {

    @Mock
    private DetalleRecetaServicio servicio;

    @InjectMocks
    private DetalleRecetaController controller;

    private DetalleReceta detalleMock;

    @BeforeEach
    void setUp() {
        detalleMock = new DetalleReceta();
    }

    @Test
    void listarOk() {
        List<DetalleReceta> listaEsperada = Arrays.asList(detalleMock, new DetalleReceta());
        when(servicio.findAll()).thenReturn(listaEsperada);

        ResponseEntity<List<DetalleReceta>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(listaEsperada, response.getBody());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void obtenerOk() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(detalleMock);

        ResponseEntity<DetalleReceta> response = controller.obtener(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(detalleMock, response.getBody());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void obtener_Ok() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(null);

        ResponseEntity<DetalleReceta> response = controller.obtener(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void crearOk() {
        when(servicio.save(any(DetalleReceta.class))).thenReturn(detalleMock);

        ResponseEntity<DetalleReceta> response = controller.crear(new DetalleReceta());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(detalleMock, response.getBody());
        verify(servicio, times(1)).save(any(DetalleReceta.class));
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        when(servicio.update(eq(id), any(DetalleReceta.class))).thenReturn(detalleMock);

        ResponseEntity<DetalleReceta> response = controller.actualizar(id, new DetalleReceta());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(detalleMock, response.getBody());
        verify(servicio, times(1)).update(eq(id), any(DetalleReceta.class));
    }

    @Test
    void actualizar_Ok() {
        Long id = 1L;
        when(servicio.update(eq(id), any(DetalleReceta.class))).thenReturn(null);

        ResponseEntity<DetalleReceta> response = controller.actualizar(id, new DetalleReceta());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).update(eq(id), any(DetalleReceta.class));
    }

    @Test
    void eliminarOk() {
        Long id = 1L;
        doNothing().when(servicio).delete(id);

        ResponseEntity<String> response = controller.eliminar(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Detalle eliminado", response.getBody());
        verify(servicio, times(1)).delete(id);
    }
}