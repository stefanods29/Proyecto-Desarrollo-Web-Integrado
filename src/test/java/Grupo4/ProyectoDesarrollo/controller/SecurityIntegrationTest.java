package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.AuthRequest;
import Grupo4.ProyectoDesarrollo.dto.RegisterRequest;
import Grupo4.ProyectoDesarrollo.model.enums.Rol;
import Grupo4.ProyectoDesarrollo.support.MockMvcIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityIntegrationTest extends MockMvcIntegrationTestBase {

    @Test
    void endpointProtegidoSinTokenRetorna401() throws Exception {
        mockMvc.perform(get("/api/pacientes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void endpointConRolIncorrectoRetorna403() throws Exception {
        RegisterRequest recepcionista = RegisterRequest.builder()
                .username("recep_sec_test")
                .password("password123")
                .nombre("Recep")
                .apellido("Sec")
                .correo("recep.sec@test.com")
                .telefono("999111333")
                .rol(Rol.RECEPCIONISTA)
                .build();

        String token = obtenerToken(recepcionista);

        mockMvc.perform(get("/api/usuarios")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void endpointConRolCorrectoRetorna200() throws Exception {
        RegisterRequest admin = RegisterRequest.builder()
                .username("admin_sec_test")
                .password("password123")
                .nombre("Admin")
                .apellido("Sec")
                .correo("admin.sec@test.com")
                .telefono("999111444")
                .rol(Rol.SUPER_ADMIN)
                .build();

        String token = obtenerToken(admin);

        mockMvc.perform(get("/api/usuarios")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String obtenerToken(RegisterRequest request) throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        AuthRequest login = AuthRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("token").asText();
    }
}
