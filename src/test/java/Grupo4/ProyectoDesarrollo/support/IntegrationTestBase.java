package Grupo4.ProyectoDesarrollo.support;

import Grupo4.ProyectoDesarrollo.model.*;
import Grupo4.ProyectoDesarrollo.model.enums.*;
import Grupo4.ProyectoDesarrollo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class IntegrationTestBase {

    private static int clinicaCounter = 0;

    @Autowired
    protected ClinicaRepository clinicaRepository;
    @Autowired
    protected EspecialidadRepository especialidadRepository;
    @Autowired
    protected UsuarioRepository usuarioRepository;
    @Autowired
    protected MedicoRepository medicoRepository;
    @Autowired
    protected ConsultorioRepository consultorioRepository;
    @Autowired
    protected PacienteRepository pacienteRepository;

    protected Clinica crearClinica(String sufijo) {
        String ruc = String.format("20%09d", ++clinicaCounter);
        Clinica clinica = Clinica.builder()
                .nombre("Clinica Test " + sufijo)
                .ruc(ruc)
                .direccion("Av. Test 123")
                .telefono("999888777")
                .correo("clinica" + sufijo + "@test.com")
                .planSuscripcion("BASICO")
                .estado(ClinicaEstado.ACTIVA)
                .build();
        return clinicaRepository.save(clinica);
    }

    protected Especialidad crearEspecialidad(String nombre) {
        Especialidad especialidad = Especialidad.builder()
                .nombre(nombre)
                .descripcion("Especialidad de prueba")
                .activa(true)
                .build();
        return especialidadRepository.save(especialidad);
    }

    protected Usuario crearUsuario(String username, Rol rol, Clinica clinica) {
        Usuario usuario = Usuario.builder()
                .username(username)
                .password("$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy")
                .nombre("Test")
                .apellido("User")
                .correo(username + "@test.com")
                .telefono("999000111")
                .rol(rol)
                .activo(true)
                .clinica(clinica)
                .build();
        return usuarioRepository.save(usuario);
    }

    protected Medico crearMedico(Clinica clinica, Especialidad especialidad, String suffix) {
        Usuario usuario = crearUsuario("medico" + suffix, Rol.MEDICO, clinica);
        Medico medico = Medico.builder()
                .numeroColegiatura("CMP" + suffix)
                .usuario(usuario)
                .especialidad(especialidad)
                .clinica(clinica)
                .activo(true)
                .build();
        return medicoRepository.save(medico);
    }

    protected Consultorio crearConsultorio(Clinica clinica, String nombre) {
        Consultorio consultorio = Consultorio.builder()
                .nombre(nombre)
                .ubicacion("Piso 1")
                .capacidad(5)
                .activo(true)
                .clinica(clinica)
                .build();
        return consultorioRepository.save(consultorio);
    }

    protected Paciente crearPaciente(Clinica clinica, String documento) {
        Paciente paciente = Paciente.builder()
                .nombre("Paciente")
                .apellido("Prueba")
                .tipoDocumento(TipoDocumento.DNI)
                .numeroDocumento(documento)
                .telefono("988776655")
                .correo(documento + "@test.com")
                .direccion("Calle Test 1")
                .fechaNacimiento(LocalDate.of(1990, 5, 15))
                .genero(Genero.MASCULINO)
                .clinica(clinica)
                .build();
        return pacienteRepository.save(paciente);
    }

    protected Cita crearCitaBase(Paciente paciente, Medico medico, Consultorio consultorio, Clinica clinica,
                                 LocalDateTime inicio, LocalDateTime fin) {
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setConsultorio(consultorio);
        cita.setClinica(clinica);
        cita.setFechaHora(inicio);
        cita.setFechaFin(fin);
        cita.setEstado(CitaEstado.PENDIENTE);
        cita.setMotivo("Consulta de control");
        return cita;
    }
}
