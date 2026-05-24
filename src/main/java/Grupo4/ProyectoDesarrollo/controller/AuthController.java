package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.AuthRequest;
import Grupo4.ProyectoDesarrollo.dto.AuthResponse;
import Grupo4.ProyectoDesarrollo.dto.RegisterRequest;
import Grupo4.ProyectoDesarrollo.dto.UsuarioDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.ClinicaRepository;
import Grupo4.ProyectoDesarrollo.security.CustomUserDetails;
import Grupo4.ProyectoDesarrollo.security.JwtService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final ClinicaRepository clinicaRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();

        String token = jwtService.generateToken(
                usuario.getUsername(),
                usuario.getRol().name(),
                usuario.getClinica() != null ? usuario.getClinica().getId() : null
        );

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .clinicaId(usuario.getClinica() != null ? usuario.getClinica().getId() : null)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        Clinica clinica = null;
        if (request.getClinicaId() != null) {
            clinica = clinicaRepository.findById(request.getClinicaId())
                    .orElseThrow(() -> new Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException(
                            "Clinica no encontrada con id: " + request.getClinicaId()));
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .correo(request.getCorreo())
                .telefono(request.getTelefono())
                .rol(request.getRol())
                .clinica(clinica)
                .activo(true)
                .build();

        Usuario nuevoUsuario = usuarioService.crear(usuario);

        String token = jwtService.generateToken(
                nuevoUsuario.getUsername(),
                nuevoUsuario.getRol().name(),
                nuevoUsuario.getClinica() != null ? nuevoUsuario.getClinica().getId() : null
        );

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .username(nuevoUsuario.getUsername())
                .rol(nuevoUsuario.getRol().name())
                .clinicaId(nuevoUsuario.getClinica() != null ? nuevoUsuario.getClinica().getId() : null)
                .build());
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
                userDetails = (CustomUserDetails) auth.getPrincipal();
            }
        }

        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(UsuarioDTO.fromEntity(userDetails.getUsuario()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout exitoso");
    }
}
