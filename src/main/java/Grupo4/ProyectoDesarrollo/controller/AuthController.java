package Grupo4.ProyectoDesarrollo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Grupo4.ProyectoDesarrollo.dto.AuthRequest;
import Grupo4.ProyectoDesarrollo.dto.AuthResponse;
import Grupo4.ProyectoDesarrollo.dto.RegisterRequest;
import Grupo4.ProyectoDesarrollo.dto.UsuarioDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.Usuario;
import Grupo4.ProyectoDesarrollo.repository.ClinicaRepository;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository;
import Grupo4.ProyectoDesarrollo.security.CustomUserDetails;
import Grupo4.ProyectoDesarrollo.security.JwtService;
import Grupo4.ProyectoDesarrollo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final ClinicaRepository clinicaRepository;
    private final PacienteRepository pacienteRepository;

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
    @Transactional(rollbackFor = Exception.class) 
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        
        Clinica clinica = null;
        if (request.getClinicaId() != null) {
            clinica = clinicaRepository.findById(request.getClinicaId())
                    .orElseThrow(() -> new Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException(
                            "Clinica no encontrada con id: " + request.getClinicaId()));
        } else {
            List<Clinica> clinicasDisponibles = clinicaRepository.findAll();
            if (!clinicasDisponibles.isEmpty()) {
                clinica = clinicasDisponibles.get(0);
            } else {
                throw new RuntimeException("Error fatal: No hay ninguna clínica creada en el sistema.");
            }
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

        if ("PACIENTE".equalsIgnoreCase(request.getRol().name())) {
            
            Grupo4.ProyectoDesarrollo.model.enums.TipoDocumento tipoDoc = 
                Grupo4.ProyectoDesarrollo.model.enums.TipoDocumento.valueOf(request.getTipoDocumento().toUpperCase());

            Grupo4.ProyectoDesarrollo.model.enums.Genero generoEnum = 
                Grupo4.ProyectoDesarrollo.model.enums.Genero.valueOf(request.getGenero().toUpperCase());

            // 🔥 LOGICA DEL SEGURO MÉDICO
            Grupo4.ProyectoDesarrollo.model.enums.SeguroMedico seguroEnum = 
                Grupo4.ProyectoDesarrollo.model.enums.SeguroMedico.valueOf(request.getSeguroMedico().toUpperCase());
            
            String numSeguroFinal = request.getNumeroSeguro();
            if (seguroEnum == Grupo4.ProyectoDesarrollo.model.enums.SeguroMedico.NINGUNO || numSeguroFinal == null || numSeguroFinal.trim().isEmpty()) {
                numSeguroFinal = "NO_TIENE";
            }

            Paciente nuevoPaciente = Paciente.builder()
                    .nombre(nuevoUsuario.getNombre())
                    .apellido(nuevoUsuario.getApellido())
                    .correo(nuevoUsuario.getCorreo())
                    .telefono(nuevoUsuario.getTelefono())
                    .usuario(nuevoUsuario) 
                    .clinica(clinica)      
                    .tipoDocumento(tipoDoc) 
                    .numeroDocumento(request.getNumeroDocumento()) 
                    .genero(generoEnum)
                    .seguroMedico(seguroEnum)         // 🔥 ASIGNA EL SEGURO
                    .numeroSeguro(numSeguroFinal)     // 🔥 ASIGNA EL NÚMERO O "NO_TIENE"
                    .direccion("Dirección Pendiente de Actualizar")
                    .fechaNacimiento(LocalDate.of(2000, 1, 1))
                    .build();

            pacienteRepository.save(nuevoPaciente);
        }

        String token = jwtService.generateToken(
                nuevoUsuario.getUsername(),
                nuevoUsuario.getRol().name(),
                nuevoUsuario.getClinica() != null ? usuario.getClinica().getId() : null
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