package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.exception.DuplicateResourceException;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.model.enums.Rol;
import Grupo4.ProyectoDesarrollo.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void noPermiteUsernameNiCorreoDuplicados() {
        Usuario primero = Usuario.builder()
                .username("dup_user")
                .password("secret123")
                .nombre("Uno")
                .apellido("Test")
                .correo("dup@test.com")
                .telefono("999000001")
                .rol(Rol.ADMIN_CLINICA)
                .activo(true)
                .build();

        usuarioService.crear(primero);

        Usuario duplicadoUsername = Usuario.builder()
                .username("dup_user")
                .password("secret123")
                .nombre("Dos")
                .apellido("Test")
                .correo("otro@test.com")
                .telefono("999000002")
                .rol(Rol.RECEPCIONISTA)
                .activo(true)
                .build();

        assertThrows(DuplicateResourceException.class, () -> usuarioService.crear(duplicadoUsername));

        Usuario duplicadoCorreo = Usuario.builder()
                .username("otro_user")
                .password("secret123")
                .nombre("Tres")
                .apellido("Test")
                .correo("dup@test.com")
                .telefono("999000003")
                .rol(Rol.RECEPCIONISTA)
                .activo(true)
                .build();

        assertThrows(DuplicateResourceException.class, () -> usuarioService.crear(duplicadoCorreo));

        Usuario guardado = usuarioService.buscarPorUsername("dup_user");
        assertTrue(passwordEncoder.matches("secret123", guardado.getPassword()));
    }
}
