package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.service.MedicamentoServicio;
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
class MedicamentoControllerTest {
    @Mock
    private MedicamentoServicio servicio;

    @InjectMocks
    private MedicamentoController controller;

    private Medicamento medicamentoMock;

    @BeforeEach
    void setUp() {
        medicamentoMock = new Medicamento(
                1L,
                "Paracetamol",
                "Acetaminofén",
                "Tableta",
                "500mg",
                "Oral",
                true
        );
    }

    @Test
    void listarOk() {
        List<Medicamento> lista = Arrays.asList(medicamentoMock, new Medicamento());
        when(servicio.findAll()).thenReturn(lista);

        ResponseEntity<List<Medicamento>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(servicio, times(1)).findAll();
    }

    @Test
    void obtenerOk() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(medicamentoMock);

        ResponseEntity<Medicamento> response = controller.obtener(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentoMock, response.getBody());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void obtenerNotFound() {
        Long id = 1L;
        when(servicio.findById(id)).thenReturn(null);

        ResponseEntity<Medicamento> response = controller.obtener(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).findById(id);
    }

    @Test
    void crearOk() {
        when(servicio.save(any(Medicamento.class))).thenReturn(medicamentoMock);

        ResponseEntity<Medicamento> response = controller.crear(new Medicamento());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentoMock, response.getBody());
        verify(servicio, times(1)).save(any(Medicamento.class));
    }

    @Test
    void actualizarOk() {
        Long id = 1L;
        when(servicio.update(eq(id), any(Medicamento.class))).thenReturn(medicamentoMock);

        ResponseEntity<Medicamento> response = controller.actualizar(id, new Medicamento());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentoMock, response.getBody());
        verify(servicio, times(1)).update(eq(id), any(Medicamento.class));
    }

    @Test
    void actualizarNotFound() {
        Long id = 1L;
        when(servicio.update(eq(id), any(Medicamento.class))).thenReturn(null);

        ResponseEntity<Medicamento> response = controller.actualizar(id, new Medicamento());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(servicio, times(1)).update(eq(id), any(Medicamento.class));
    }

    @Test
    void eliminarOk() {
        Long id = 1L;
        doNothing().when(servicio).delete(id);

        ResponseEntity<String> response = controller.eliminar(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Medicamento eliminado", response.getBody());
        verify(servicio, times(1)).delete(id);
    }
}