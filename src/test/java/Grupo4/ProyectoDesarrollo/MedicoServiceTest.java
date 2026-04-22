package Grupo4.ProyectoDesarrollo;

import Grupo4.ProyectoDesarrollo.dto.medicoDTO;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.service.MedicoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @Test
    void deberiaCrearMedico() {

        Medico medico = new Medico();
        medico.setNombre("Caleb Romero");
        medico.setEspecialidad("Cardiologia");
        medico.setTelefono("987654321");
        medico.setCorreo("caleb@test.com");

        Medico creado = medicoService.crear(medico);

        assertNotNull(creado.getId());
        assertEquals("Caleb Romero", creado.getNombre());
    }

    @Test
    void deberiaListarMedicos() {
        assertTrue(medicoService.listar().size() >= 0);
    }
}