package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.citaDTO;

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
        List<citaDTO> resultado = controller.listarCitas();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testListarCitasConDatos() {
        citaDTO cita1 = new citaDTO();
        cita1.setPacienteId(1L);
        cita1.setMedicoId(10L);
        cita1.setEstado("PENDIENTE");
        citaDTO cita2 = new citaDTO();
        cita2.setPacienteId(2L);
        cita2.setMedicoId(20L);
        cita2.setEstado("CONFIRMADA");

        controller.crearCita(cita1);
        controller.crearCita(cita2);
        List<citaDTO> resultado = controller.listarCitas();
        assertEquals(2, resultado.size());
    }

    // obtenerCita()
    @Test
    void testObtenerCita() {
        citaDTO cita = new citaDTO();
        cita.setPacienteId(1L);
        cita.setMedicoId(10L);
        cita.setEstado("PENDIENTE");
        citaDTO creada = controller.crearCita(cita); // ID asignado = 1
        citaDTO resultado = controller.obtenerCita(creada.getId());
        assertNotNull(resultado);
        assertEquals(creada.getId(), resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    void testObtenerCitaidInexistente() {
        citaDTO resultado = controller.obtenerCita(99L);
        assertNull(resultado);
    }

    // crearCita()
    @Test
    void testCrearCita() {
        citaDTO cita = new citaDTO();
        cita.setPacienteId(5L);
        cita.setMedicoId(15L);
        cita.setEstado("PENDIENTE");
        citaDTO resultado = controller.crearCita(cita);
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(1L, resultado.getId()); // Primera cita → ID = 1
        assertEquals(1, controller.listarCitas().size());
    }

    @Test
    void testCrearCitaidIncremental() {
        citaDTO cita1 = new citaDTO();
        cita1.setEstado("PENDIENTE");
        citaDTO cita2 = new citaDTO();
        cita2.setEstado("CONFIRMADA");
        citaDTO primera = controller.crearCita(cita1);
        citaDTO segunda = controller.crearCita(cita2);
        assertEquals(1L, primera.getId());
        assertEquals(2L, segunda.getId());
        assertEquals(2, controller.listarCitas().size());
    }

    // actualizarCita()
    @Test
    void testActualizarCita() {
        citaDTO cita = new citaDTO();
        cita.setPacienteId(1L);
        cita.setMedicoId(10L);
        cita.setEstado("PENDIENTE");
        citaDTO creada = controller.crearCita(cita);
        citaDTO datosActualizados = new citaDTO();
        datosActualizados.setPacienteId(2L);
        datosActualizados.setMedicoId(20L);
        datosActualizados.setEstado("CONFIRMADA");
        // datosActualizados.setFechaHora(...); // Configura si usas LocalDateTime
        citaDTO resultado = controller.actualizarCita(creada.getId(), datosActualizados);
        assertNotNull(resultado);
        assertEquals(2L, resultado.getPacienteId());
        assertEquals(20L, resultado.getMedicoId());
        assertEquals("CONFIRMADA", resultado.getEstado());
    }

    @Test
    void testActualizarCitaidInexistente() {
        citaDTO datosActualizados = new citaDTO();
        datosActualizados.setEstado("CANCELADA");
        citaDTO resultado = controller.actualizarCita(99L, datosActualizados);
        assertNull(resultado);
    }

    // eliminarCita()
    @Test
    void testEliminarCita() {
        citaDTO cita = new citaDTO();
        cita.setEstado("PENDIENTE");
        citaDTO creada = controller.crearCita(cita);
        String mensaje = controller.eliminarCita(creada.getId());
        assertEquals("Cita eliminada", mensaje);
        assertEquals(0, controller.listarCitas().size());
        assertNull(controller.obtenerCita(creada.getId())); // Ya no existe
    }

    @Test
    void testEliminarCitaidInexistente() {
        citaDTO cita = new citaDTO();
        cita.setEstado("PENDIENTE");
        controller.crearCita(cita);
        String mensaje = controller.eliminarCita(99L); // ID que no existe
        assertEquals("Cita eliminada", mensaje);
        assertEquals(1, controller.listarCitas().size()); // La cita original sigue ahi
    }
}
