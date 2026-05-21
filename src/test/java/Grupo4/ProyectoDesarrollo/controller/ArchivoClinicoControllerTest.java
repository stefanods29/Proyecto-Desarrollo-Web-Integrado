package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.ArchivoClinicoDTO;
import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.service.ArchivoClinicoServicio;
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
public class ArchivoClinicoControllerTest {
    @Mock
    private ArchivoClinicoServicio servicio;
    @Mock
    private ConsultaMedicaServicio consultaMedicaServicio;

    @InjectMocks
    private ArchivoClinicoController controller;

    private ArchivoClinico archivo;
    private ConsultaMedica consultaMock;

    @BeforeEach
    void setUp() {
        consultaMock = new ConsultaMedica();
        consultaMock.setId(5L);

        archivo = new ArchivoClinico();
        archivo.setId(1L);
        archivo.setConsultaMedica(consultaMock);
    }

    @Test
    void testListarStatus200() {
        List<ArchivoClinico> lista = Arrays.asList(archivo, archivo);
        when(servicio.findAll()).thenReturn(lista);
        ResponseEntity<List<ArchivoClinicoDTO>> respuesta = controller.listar();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(2, respuesta.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void testListar() {
        when(servicio.findAll()).thenReturn(List.of());
        ResponseEntity<List<ArchivoClinicoDTO>> respuesta = controller.listar();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().isEmpty());
    }

    @Test
    void testObtenerid() {
        when(servicio.findById(1L)).thenReturn(archivo);
        ResponseEntity<ArchivoClinicoDTO> respuesta = controller.obtener(1L);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getId());
        verify(servicio, times(1)).findById(1L);
    }

    @Test
    void testObtener() {
        when(servicio.findById(99L)).thenReturn(null);
        ResponseEntity<ArchivoClinicoDTO> respuesta = controller.obtener(99L);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
        verify(servicio, times(1)).findById(99L);
    }

    @Test
    void testCrear() {
        when(consultaMedicaServicio.findById(any())).thenReturn(consultaMock);
        when(servicio.save(any(ArchivoClinico.class))).thenReturn(archivo);

        ArchivoClinicoDTO dto = ArchivoClinicoDTO.fromEntity(archivo);
        ResponseEntity<ArchivoClinicoDTO> respuesta = controller.crear(dto);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(archivo.getId(), respuesta.getBody().getId());
        verify(servicio, times(1)).save(any(ArchivoClinico.class));
    }

    @Test
    void testActualizarid() {
        when(consultaMedicaServicio.findById(any())).thenReturn(consultaMock);
        ArchivoClinico actualizado = new ArchivoClinico();
        actualizado.setId(1L);
        actualizado.setConsultaMedica(consultaMock);
        when(servicio.update(eq(1L), any(ArchivoClinico.class))).thenReturn(actualizado);

        ArchivoClinicoDTO dto = ArchivoClinicoDTO.fromEntity(archivo);
        ResponseEntity<ArchivoClinicoDTO> respuesta = controller.actualizar(1L, dto);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getId());
        verify(servicio, times(1)).update(eq(1L), any(ArchivoClinico.class));
    }

    @Test
    void testActualizaridInexistente() {
        when(consultaMedicaServicio.findById(any())).thenReturn(consultaMock);
        when(servicio.update(eq(99L), any(ArchivoClinico.class))).thenReturn(null);

        ArchivoClinicoDTO dto = ArchivoClinicoDTO.fromEntity(archivo);
        ResponseEntity<ArchivoClinicoDTO> respuesta = controller.actualizar(99L, dto);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
        verify(servicio, times(1)).update(eq(99L), any(ArchivoClinico.class));
    }

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
