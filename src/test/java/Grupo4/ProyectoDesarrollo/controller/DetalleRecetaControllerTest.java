package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.DetalleRecetaDTO;
import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.DetalleReceta;
import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.service.DetalleRecetaServicio;
import Grupo4.ProyectoDesarrollo.service.MedicamentoServicio;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleRecetaControllerTest {

    @Mock
    private DetalleRecetaServicio servicio;
    @Mock
    private RecetaServicio recetaServicio;
    @Mock
    private MedicamentoServicio medicamentoServicio;

    @InjectMocks
    private DetalleRecetaController controller;

    private DetalleReceta detalleMock;
    private Receta recetaMock;
    private Medicamento medicamentoMock;

    @BeforeEach
    void setUp() {
        recetaMock = new Receta();
        recetaMock.setId(5L);
        medicamentoMock = new Medicamento();
        medicamentoMock.setId(10L);

        detalleMock = new DetalleReceta();
        detalleMock.setId(1L);
        detalleMock.setReceta(recetaMock);
        detalleMock.setMedicamento(medicamentoMock);
    }

    @Test
    void listarOk() {
        List<DetalleReceta> listaEsperada = Arrays.asList(detalleMock, detalleMock);
        when(servicio.findAll()).thenReturn(listaEsperada);

        ResponseEntity<List<DetalleRecetaDTO>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void obtenerOk() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(detalleMock);

        ResponseEntity<DetalleRecetaDTO> response = controller.obtener(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void obtener_Ok() {
        Long id = 1L;
        when(servicio.findById(id)).thenThrow(new ResourceNotFoundException("Detalle de receta no encontrado con id: 1"));

        assertThrows(ResourceNotFoundException.class, () -> controller.obtener(id));
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void crearOk() {
        when(recetaServicio.findById(any())).thenReturn(recetaMock);
        when(medicamentoServicio.findById(any())).thenReturn(medicamentoMock);
        when(servicio.save(any(DetalleReceta.class))).thenReturn(detalleMock);

        DetalleRecetaDTO dto = DetalleRecetaDTO.fromEntity(detalleMock);
        ResponseEntity<DetalleRecetaDTO> response = controller.crear(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).save(any(DetalleReceta.class));
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        when(recetaServicio.findById(any())).thenReturn(recetaMock);
        when(medicamentoServicio.findById(any())).thenReturn(medicamentoMock);
        when(servicio.update(eq(id), any(DetalleReceta.class))).thenReturn(detalleMock);

        DetalleRecetaDTO dto = DetalleRecetaDTO.fromEntity(detalleMock);
        ResponseEntity<DetalleRecetaDTO> response = controller.actualizar(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(servicio, times(1)).update(eq(id), any(DetalleReceta.class));
    }

    @Test
    void actualizar_Ok() {
        Long id = 1L;
        when(recetaServicio.findById(any())).thenReturn(recetaMock);
        when(medicamentoServicio.findById(any())).thenReturn(medicamentoMock);
        when(servicio.update(eq(id), any(DetalleReceta.class)))
                .thenThrow(new ResourceNotFoundException("Detalle de receta no encontrado con id: 1"));

        DetalleRecetaDTO dto = DetalleRecetaDTO.fromEntity(detalleMock);
        assertThrows(ResourceNotFoundException.class, () -> controller.actualizar(id, dto));
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