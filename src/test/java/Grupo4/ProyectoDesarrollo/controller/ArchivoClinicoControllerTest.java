package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.service.ArchivoClinicoServicio;

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
public class ArchivoClinicoControllerTest {
    @Mock
    private ArchivoClinicoServicio servicio;
    @InjectMocks
    private ArchivoClinicoController controller;
    private ArchivoClinico archivo;
    @BeforeEach
    void setUp() {archivo = new ArchivoClinico();archivo.setId(1L);}

    // listar
    @Test
    void testListarStatus200() {
        List<ArchivoClinico> lista = Arrays.asList(archivo, new ArchivoClinico());
        when(servicio.findAll()).thenReturn(lista);
        ResponseEntity<List<ArchivoClinico>> respuesta = controller.listar();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(2, respuesta.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void testListar() {
        when(servicio.findAll()).thenReturn(List.of());
        ResponseEntity<List<ArchivoClinico>> respuesta = controller.listar();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().isEmpty());
    }

    // obtener()
    @Test
    void testObtenerid() {
        when(servicio.findById(1L)).thenReturn(archivo);
        ResponseEntity<ArchivoClinico> respuesta = controller.obtener(1L);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getId());
        verify(servicio, times(1)).findById(1L);
    }

    @Test
    void testObtener() {
        when(servicio.findById(99L)).thenReturn(null);
        ResponseEntity<ArchivoClinico> respuesta = controller.obtener(99L);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
        verify(servicio, times(1)).findById(99L);
    }

    // crear()
    @Test
    void testCrear() {
        when(servicio.save(archivo)).thenReturn(archivo);
        ResponseEntity<ArchivoClinico> respuesta = controller.crear(archivo);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(archivo.getId(), respuesta.getBody().getId());
        verify(servicio, times(1)).save(archivo);
    }

    @Test
    void testCrearArchivoNuevo() {
        ArchivoClinico nuevo = new ArchivoClinico();
        when(servicio.save(nuevo)).thenReturn(nuevo);
        controller.crear(nuevo);
        verify(servicio, times(1)).save(nuevo);
    }

    // actualizar()
    @Test
    void testActualizarid() {
        ArchivoClinico actualizado = new ArchivoClinico();
        actualizado.setId(1L);
        when(servicio.update(1L, actualizado)).thenReturn(actualizado);
        ResponseEntity<ArchivoClinico> respuesta = controller.actualizar(1L, actualizado);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getId());
        verify(servicio, times(1)).update(1L, actualizado);
    }

    @Test
    void testActualizaridInexistente() {
        when(servicio.update(99L, archivo)).thenReturn(null);
        ResponseEntity<ArchivoClinico> respuesta = controller.actualizar(99L, archivo);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
        verify(servicio, times(1)).update(99L, archivo);
    }

    // eliminar()
    @Test
    void testEliminar() {
        doNothing().when(servicio).delete(1L);
        ResponseEntity<String> respuesta = controller.eliminar(1L);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Archivo Eliminado", respuesta.getBody());
        verify(servicio, times(1)).delete(1L);
    }

    @Test
    void testEliminarId() {
        doNothing().when(servicio).delete(5L);
        controller.eliminar(5L);
        verify(servicio, times(1)).delete(5L);
        verify(servicio, never()).delete(1L);
    }
}
