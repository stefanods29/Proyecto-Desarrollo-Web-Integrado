package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RecetaControllerTest {

    @Mock
    private RecetaServicio servicio;

    @InjectMocks
    private RecetaController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListar() {
        when(servicio.findAll()).thenReturn(List.of(new Receta()));

        ResponseEntity<List<Receta>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testObtener_OK() {
        Receta receta = new Receta();
        receta.setId(1L);

        when(servicio.findById(1L)).thenReturn(receta);

        ResponseEntity<Receta> response = controller.obtener(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testObtener_NotFound() {
        when(servicio.findById(1L)).thenReturn(null);

        ResponseEntity<Receta> response = controller.obtener(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCrear() {
        Receta receta = new Receta();

        when(servicio.save(receta)).thenReturn(receta);

        ResponseEntity<Receta> response = controller.crear(receta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testActualizar_OK() {
        Receta receta = new Receta();

        when(servicio.update(1L, receta)).thenReturn(receta);

        ResponseEntity<Receta> response = controller.actualizar(1L, receta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testActualizar_NotFound() {
        Receta receta = new Receta();

        when(servicio.update(1L, receta)).thenReturn(null);

        ResponseEntity<Receta> response = controller.actualizar(1L, receta);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testEliminar() {
        doNothing().when(servicio).delete(1L);

        ResponseEntity<String> response = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Receta eliminada", response.getBody());
    }
}