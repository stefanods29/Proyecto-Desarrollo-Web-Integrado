package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.dto.RecetaDTO;
import Grupo4.ProyectoDesarrollo.model.ConsultaMedica;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.Receta;
import Grupo4.ProyectoDesarrollo.service.ConsultaMedicaServicio;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import Grupo4.ProyectoDesarrollo.service.RecetaServicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class RecetaControllerTest {

    @Mock
    private RecetaServicio servicio;
    @Mock
    private ConsultaMedicaServicio consultaMedicaServicio;
    @Mock
    private MedicoService medicoService;
    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private RecetaController controller;

    private Receta recetaMock;
    private ConsultaMedica consultaMock;
    private Medico medicoMock;
    private Paciente pacienteMock;

    @BeforeEach
    void setUp() {
        consultaMock = new ConsultaMedica();
        consultaMock.setId(5L);
        medicoMock = new Medico();
        medicoMock.setId(3L);
        pacienteMock = Paciente.builder().id(2L).build();

        recetaMock = new Receta();
        recetaMock.setId(1L);
        recetaMock.setConsultaMedica(consultaMock);
        recetaMock.setMedico(medicoMock);
        recetaMock.setPaciente(pacienteMock);
    }

    @Test
    void testListar() {
        when(servicio.findAll()).thenReturn(List.of(recetaMock));

        ResponseEntity<List<RecetaDTO>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testObtener_OK() {
        when(servicio.findById(1L)).thenReturn(recetaMock);

        ResponseEntity<RecetaDTO> response = controller.obtener(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testObtener_NotFound() {
        when(servicio.findById(1L)).thenReturn(null);

        ResponseEntity<RecetaDTO> response = controller.obtener(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCrear() {
        when(consultaMedicaServicio.findById(any())).thenReturn(consultaMock);
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(servicio.save(any(Receta.class))).thenReturn(recetaMock);

        RecetaDTO dto = RecetaDTO.fromEntity(recetaMock);
        ResponseEntity<RecetaDTO> response = controller.crear(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testActualizar_OK() {
        when(consultaMedicaServicio.findById(any())).thenReturn(consultaMock);
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(servicio.update(eq(1L), any(Receta.class))).thenReturn(recetaMock);

        RecetaDTO dto = RecetaDTO.fromEntity(recetaMock);
        ResponseEntity<RecetaDTO> response = controller.actualizar(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testActualizar_NotFound() {
        when(consultaMedicaServicio.findById(any())).thenReturn(consultaMock);
        when(medicoService.buscarPorId(any())).thenReturn(medicoMock);
        when(pacienteService.buscarPorId(any())).thenReturn(pacienteMock);
        when(servicio.update(eq(1L), any(Receta.class))).thenReturn(null);

        RecetaDTO dto = RecetaDTO.fromEntity(recetaMock);
        ResponseEntity<RecetaDTO> response = controller.actualizar(1L, dto);

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