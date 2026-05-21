package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.dto.PacienteDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.PacienteService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private PacienteService service;
    @Mock
    private ClinicaService clinicaService;
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private PacienteController controller;

    private Clinica clinicaMock;
    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(2L);
        usuarioMock = new Usuario();
        usuarioMock.setId(3L);
        usuarioMock.setClinica(clinicaMock);
    }

    @Test
    void testCrear() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setClinica(clinicaMock);
        paciente.setUsuario(usuarioMock);

        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(usuarioService.buscarPorId(any())).thenReturn(usuarioMock);
        when(service.crear(any(Paciente.class))).thenReturn(paciente);

        PacienteDTO dto = PacienteDTO.fromEntity(paciente);
        PacienteDTO result = controller.crear(dto);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void testListar() {
        Paciente paciente = new Paciente();
        paciente.setClinica(clinicaMock);
        paciente.setUsuario(usuarioMock);
        when(service.listar()).thenReturn(List.of(paciente));

        List<PacienteDTO> lista = controller.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setClinica(clinicaMock);
        paciente.setUsuario(usuarioMock);

        when(service.buscarPorId(1L)).thenReturn(paciente);

        PacienteDTO result = controller.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testActualizar() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Actualizado");
        paciente.setClinica(clinicaMock);
        paciente.setUsuario(usuarioMock);

        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(usuarioService.buscarPorId(any())).thenReturn(usuarioMock);
        when(service.crear(any(Paciente.class))).thenReturn(paciente);

        PacienteDTO dto = PacienteDTO.fromEntity(paciente);
        PacienteDTO result = controller.actualizar(1L, dto);

        assertNotNull(result);
        assertEquals("Actualizado", result.getNombre());
    }

    @Test
    void testEliminar() {
        doNothing().when(service).eliminar(1L);

        controller.eliminar(1L);

        verify(service, times(1)).eliminar(1L);
    }
}