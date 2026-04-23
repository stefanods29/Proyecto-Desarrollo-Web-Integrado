package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.CitaDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CitaControllerTest {

    private CitaController controller;

    @BeforeEach
    void setUp() {
        // Se instancia directamente porque el controller no depende de un servicio externo
        controller = new CitaController();
    }
    // listarCitas()
    @Test
    void testListarCitas() {
        List<CitaDTO> resultado = controller.listarCitas();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testListarCitasConDatos() {
        CitaDTO cita1 = new CitaDTO();
        cita1.setPacienteId(1L);
        cita1.setMedicoId(10L);
        cita1.setEstado("PENDIENTE");
        CitaDTO cita2 = new CitaDTO();
        cita2.setPacienteId(2L);
        cita2.setMedicoId(20L);
        cita2.setEstado("CONFIRMADA");

        controller.crearCita(cita1);
        controller.crearCita(cita2);
        List<CitaDTO> resultado = controller.listarCitas();
        assertEquals(2, resultado.size());
    }

    // obtenerCita()
    @Test
    void testObtenerCita() {
        CitaDTO cita = new CitaDTO();
        cita.setPacienteId(1L);
        cita.setMedicoId(10L);
        cita.setEstado("PENDIENTE");
        CitaDTO creada = controller.crearCita(cita); // ID asignado = 1
        CitaDTO resultado = controller.obtenerCita(creada.getId());
        assertNotNull(resultado);
        assertEquals(creada.getId(), resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    void testObtenerCitaidInexistente() {
        CitaDTO resultado = controller.obtenerCita(99L);
        assertNull(resultado);
    }

    // crearCita()
    @Test
    void testCrearCita() {
        CitaDTO cita = new CitaDTO();
        cita.setPacienteId(5L);
        cita.setMedicoId(15L);
        cita.setEstado("PENDIENTE");
        CitaDTO resultado = controller.crearCita(cita);
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(1L, resultado.getId()); // Primera cita → ID = 1
        assertEquals(1, controller.listarCitas().size());
    }

    @Test
    void testCrearCitaidIncremental() {
        CitaDTO cita1 = new CitaDTO();
        cita1.setEstado("PENDIENTE");
        CitaDTO cita2 = new CitaDTO();
        cita2.setEstado("CONFIRMADA");
        CitaDTO primera = controller.crearCita(cita1);
        CitaDTO segunda = controller.crearCita(cita2);
        assertEquals(1L, primera.getId());
        assertEquals(2L, segunda.getId());
        assertEquals(2, controller.listarCitas().size());
    }

    // actualizarCita()
    @Test
    void testActualizarCita() {
        CitaDTO cita = new CitaDTO();
        cita.setPacienteId(1L);
        cita.setMedicoId(10L);
        cita.setEstado("PENDIENTE");
        CitaDTO creada = controller.crearCita(cita);
        CitaDTO datosActualizados = new CitaDTO();
        datosActualizados.setPacienteId(2L);
        datosActualizados.setMedicoId(20L);
        datosActualizados.setEstado("CONFIRMADA");
        CitaDTO resultado = controller.actualizarCita(creada.getId(), datosActualizados);
        assertNotNull(resultado);
        assertEquals(2L, resultado.getPacienteId());
        assertEquals(20L, resultado.getMedicoId());
        assertEquals("CONFIRMADA", resultado.getEstado());
    }

    @Test
    void testActualizarCitaidInexistente() {
        CitaDTO datosActualizados = new CitaDTO();
        datosActualizados.setEstado("CANCELADA");
        CitaDTO resultado = controller.actualizarCita(99L, datosActualizados);
        assertNull(resultado);
    }

    @Test
    void testEliminarCita() {
        CitaDTO cita = new CitaDTO();
        cita.setEstado("PENDIENTE");
        CitaDTO creada = controller.crearCita(cita);
        String mensaje = controller.eliminarCita(creada.getId());
        assertEquals("Cita eliminada", mensaje);
        assertEquals(0, controller.listarCitas().size());
        assertNull(controller.obtenerCita(creada.getId())); 
    }

    @Test
    void testEliminarCitaidInexistente() {
        CitaDTO cita = new CitaDTO();
        cita.setEstado("PENDIENTE");
        controller.crearCita(cita);
        String mensaje = controller.eliminarCita(99L); 
        assertEquals("Cita eliminada", mensaje);
        assertEquals(1, controller.listarCitas().size()); 
    }
}
