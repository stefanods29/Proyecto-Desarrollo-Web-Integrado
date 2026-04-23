package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.repository.ArchivoClinicoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArchivoClinicoServicioImplTest {
    @Mock
    private ArchivoClinicoRepository repository;
    @InjectMocks
    private ArchivoClinicoServicioImpl servicio;
    private ArchivoClinico archivo;

    @BeforeEach
    void setUp() {
        archivo = new ArchivoClinico();
        archivo.setId(1L);
        archivo.setNombreArchivo("historial_001.pdf");
        archivo.setTipoArchivo("PDF");
        archivo.setRutaArchivo("/archivos/historial_001.pdf");
        archivo.setDescripcion("Historial clínico del paciente");
        // archivo.setConsultaMedica(...); // Configura si tienes el objeto disponible
        // archivo.setFechaSubida(LocalDate.now());
    }

    // findAll()
    @Test
    void testFindAll() {
        List<ArchivoClinico> lista = Arrays.asList(archivo, new ArchivoClinico());
        when(repository.findAll()).thenReturn(lista);
        List<ArchivoClinico> resultado = servicio.findAll();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindAll_retornaListaVacia() {
        when(repository.findAll()).thenReturn(List.of());
        List<ArchivoClinico> resultado = servicio.findAll();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    // fidnById()
    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(archivo));
        ArchivoClinico resultado = servicio.findById(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("historial_001.pdf", resultado.getNombreArchivo());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_Inexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        ArchivoClinico resultado = servicio.findById(99L);
        assertNull(resultado);
        verify(repository, times(1)).findById(99L);
    }

    // save()
    @Test
    void testSave() {
        when(repository.save(archivo)).thenReturn(archivo);
        ArchivoClinico resultado = servicio.save(archivo);
        assertNotNull(resultado);
        assertEquals(archivo.getId(), resultado.getId());
        assertEquals(archivo.getNombreArchivo(), resultado.getNombreArchivo());
        verify(repository, times(1)).save(archivo);
    }

    @Test
    void testSavellamaRepositorio() {
        // Arrange
        ArchivoClinico nuevo = new ArchivoClinico();
        when(repository.save(nuevo)).thenReturn(nuevo);

        // Act
        servicio.save(nuevo);

        // Assert
        verify(repository, times(1)).save(nuevo);
    }

    // update()
    @Test
    void testUpdate_idExistente_actualizaCamposYRetornaArchivo() {
        ArchivoClinico datosNuevos = new ArchivoClinico();
        datosNuevos.setNombreArchivo("historial_actualizado.pdf");
        datosNuevos.setTipoArchivo("JPG");
        datosNuevos.setRutaArchivo("/archivos/actualizado.pdf");
        datosNuevos.setDescripcion("Descripción actualizada");
        // datosNuevos.setConsultaMedica(...);
        // datosNuevos.setFechaSubida(LocalDate.now());

        when(repository.findById(1L)).thenReturn(Optional.of(archivo));
        when(repository.save(archivo)).thenReturn(archivo);
        ArchivoClinico resultado = servicio.update(1L, datosNuevos);
        assertNotNull(resultado);
        assertEquals("historial_actualizado.pdf", resultado.getNombreArchivo());
        assertEquals("JPG", resultado.getTipoArchivo());
        assertEquals("/archivos/actualizado.pdf", resultado.getRutaArchivo());
        assertEquals("Descripción actualizada", resultado.getDescripcion());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(archivo);
    }

    @Test
    void testUpdateidInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        ArchivoClinico resultado = servicio.update(99L, archivo);
        assertNull(resultado);
        verify(repository, times(1)).findById(99L);
        verify(repository, never()).save(any()); // nunca debe guardar si no existe
    }

    // delete()
    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);
        servicio.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_noLlamaConOtroId() {
        doNothing().when(repository).deleteById(3L);
        servicio.delete(3L);
        verify(repository, times(1)).deleteById(3L);
        verify(repository, never()).deleteById(1L); // verifica que no elimino otro registro
    }
}
