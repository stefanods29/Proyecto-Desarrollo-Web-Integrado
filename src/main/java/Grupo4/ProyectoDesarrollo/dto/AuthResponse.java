package Grupo4.ProyectoDesarrollo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String username;
    private String rol;
    private Long clinicaId;
}
