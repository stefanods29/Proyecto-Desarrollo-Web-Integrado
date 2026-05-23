package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.exception.BusinessRuleException;
import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import Grupo4.ProyectoDesarrollo.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CitaServiceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaRepository citaRepository;

    @Test
    void noPermiteSolapamientoDeHorarioDelMedico() {
        Clinica clinica = crearClinica("A");
        Especialidad especialidad = crearEspecialidad("Medicina General");
        Medico medico = crearMedico(clinica, especialidad, "1");
        Consultorio consultorio1 = crearConsultorio(clinica, "C1");
        Consultorio consultorio2 = crearConsultorio(clinica, "C2");
        Paciente paciente = crearPaciente(clinica, "11111111");

        LocalDateTime inicio = LocalDateTime.now().plusDays(2).withHour(10).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fin = inicio.plusHours(1);

        Cita primera = crearCitaBase(paciente, medico, consultorio1, clinica, inicio, fin);
        citaService.crear(primera);

        Cita segunda = crearCitaBase(paciente, medico, consultorio2, clinica, inicio.plusMinutes(30), fin.plusMinutes(30));

        assertThrows(BusinessRuleException.class, () -> citaService.crear(segunda));
        assertEquals(1, citaRepository.count());
    }
}
