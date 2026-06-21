package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.AuthRequest;
import Grupo4.ProyectoDesarrollo.dto.AuthResponse;
import Grupo4.ProyectoDesarrollo.dto.RegisterRequest;
import Grupo4.ProyectoDesarrollo.dto.UsuarioDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Paciente; // NUEVO
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.ClinicaRepository;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository; // NUEVO
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
    
    // 1. INYECTAMOS EL REPOSITORIO DE PACIENTE
    private final PacienteRepository pacienteRepository; 

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        // ... (Tu código actual del login se mantiene igual)
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

        // Paso A: Crear el Usuario de Login
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

        // 2. SOLUCIÓN: SI ES PACIENTE, CREAR LA ENTIDAD PACIENTE
        if ("PACIENTE".equalsIgnoreCase(request.getRol().name())) {
            
            // Limitamos el username a 20 caracteres para que no rompa el campo DNI
            String documentoTemporal = nuevoUsuario.getUsername();
            if(documentoTemporal.length() > 20) {
                documentoTemporal = documentoTemporal.substring(0, 20);
            }

            Paciente nuevoPaciente = Paciente.builder()
                    .nombre(nuevoUsuario.getNombre())
                    .apellido(nuevoUsuario.getApellido())
                    .correo(nuevoUsuario.getCorreo())
                    .telefono(nuevoUsuario.getTelefono())
                    .usuario(nuevoUsuario)
                    .clinica(clinica) // Ojo: Si en tu form no mandan clínica, esto será null y chocará con tu BD.
                    // Rellenamos datos obligatorios de BD con temporales para que no explote
                    .tipoDocumento(Grupo4.ProyectoDesarrollo.model.enums.TipoDocumento.DNI)
                    .numeroDocumento(documentoTemporal) 
                    .direccion("Dirección Pendiente de Actualizar")
                    .fechaNacimiento(java.time.LocalDate.of(2000, 1, 1))
                    .genero(Grupo4.ProyectoDesarrollo.model.enums.Genero.OTRO)
                    .build();

            pacienteRepository.save(nuevoPaciente);
        }

        // Paso C: Generar Token y Devolver
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

    // ... (Tu código actual del me() y logout() se mantiene igual)
}