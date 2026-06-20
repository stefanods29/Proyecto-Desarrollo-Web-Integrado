package Grupo4.ProyectoDesarrollo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import Grupo4.ProyectoDesarrollo.dto.MedicoDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Especialidad;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.EspecialidadService;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class MedicoControllerTest {

    @Mock
    private MedicoService service;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private EspecialidadService especialidadService;
    @Mock
    private ClinicaService clinicaService;

    @InjectMocks
    private MedicoController controller;

    private Clinica clinicaMock;
    private Usuario usuarioMock;
    private Especialidad especialidadMock;
    private Medico medicoMock;

    @BeforeEach
    void setUp() {
        clinicaMock = new Clinica();
        clinicaMock.setId(2L);
        usuarioMock = new Usuario();
        usuarioMock.setId(3L);
        usuarioMock.setClinica(clinicaMock);
        especialidadMock = new Especialidad();
        especialidadMock.setId(4L);

        medicoMock = new Medico();
        medicoMock.setId(1L);
        medicoMock.setClinica(clinicaMock);
        medicoMock.setUsuario(usuarioMock);
        medicoMock.setEspecialidad(especialidadMock);
    }

    @Test
    void testCrear() {
        when(usuarioService.buscarPorId(any())).thenReturn(usuarioMock);
        when(especialidadService.buscarPorId(any())).thenReturn(especialidadMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(Medico.class))).thenReturn(medicoMock);

        MedicoDTO dto = MedicoDTO.fromEntity(medicoMock);
        MedicoDTO result = controller.crear(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testListar() {
        when(service.listar()).thenReturn(List.of(medicoMock));

        List<MedicoDTO> lista = controller.listar();

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        when(service.buscarPorId(1L)).thenReturn(medicoMock);

        ResponseEntity<MedicoDTO> response = controller.buscarPorId(1L);
        MedicoDTO result = response.getBody();

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testActualizar() {
        when(usuarioService.buscarPorId(any())).thenReturn(usuarioMock);
        when(especialidadService.buscarPorId(any())).thenReturn(especialidadMock);
        when(clinicaService.buscarPorId(any())).thenReturn(clinicaMock);
        when(service.crear(any(Medico.class))).thenReturn(medicoMock);

        MedicoDTO dto = MedicoDTO.fromEntity(medicoMock);
        MedicoDTO result = controller.actualizar(1L, dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testEliminar() {
        doNothing().when(service).eliminar(1L);

        controller.eliminar(1L);

        verify(service, times(1)).eliminar(1L);
    }
}