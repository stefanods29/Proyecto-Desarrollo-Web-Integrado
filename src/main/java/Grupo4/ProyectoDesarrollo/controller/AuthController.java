package Grupo4.ProyectoDesarrollo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public String login() {
        return "Login exitoso";
    }

    @PostMapping("/register")
    public String register() {
        return "Usuario registrado exitosamente";
    }

    @GetMapping("/me")
    public String me() {
        return "Detalles de usuario actual";
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout exitoso";
    }
}
