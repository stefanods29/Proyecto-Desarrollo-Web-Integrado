package Grupo4.ProyectoDesarrollo.config;

import Grupo4.ProyectoDesarrollo.security.JwtAuthenticationFilter;
import Grupo4.ProyectoDesarrollo.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.http.HttpStatus;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Autenticación abierta
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/me", "/api/auth/logout").authenticated()

                        // Clínicas: consultas públicas, administración solo SUPER_ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/clinicas", "/api/clinicas/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clinicas").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/clinicas/*").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clinicas/*").hasRole("SUPER_ADMIN")

                        // Usuarios: solo administradores de clínica y super admin
                        .requestMatchers("/api/usuarios", "/api/usuarios/*").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")

                        // Pacientes: acceso a personal de clínica y al paciente
                        .requestMatchers(HttpMethod.GET, "/api/pacientes", "/api/pacientes/*", "/api/pacientes/buscar")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO")
                        .requestMatchers(HttpMethod.POST, "/api/pacientes")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/pacientes/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/pacientes/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO", "PACIENTE")

                        // Catálogos médicos: lectura para el equipo clínico, edición solo para
                        // administración
                        .requestMatchers(HttpMethod.GET,
                                "/api/medicos", "/api/medicos/*",
                                "/api/especialidades", "/api/especialidades/*",
                                "/api/consultorios", "/api/consultorios/*",
                                "/api/horarios", "/api/horarios/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "RECEPCIONISTA", "ENFERMERA")
                        .requestMatchers(HttpMethod.POST,
                                "/api/medicos", "/api/especialidades", "/api/consultorios", "/api/horarios")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/medicos/*", "/api/especialidades/*", "/api/consultorios/*", "/api/horarios/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/medicos/*", "/api/especialidades/*", "/api/consultorios/*", "/api/horarios/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")

                        // Citas: paciente y personal autorizado pueden acceder
                        .requestMatchers("/api/citas", "/api/citas/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO", "PACIENTE")

                        // Expedientes y recetas: lectura para personal clínico y paciente; escritura
                        // para personal autorizado
                        .requestMatchers(HttpMethod.GET,
                                "/api/consulta-medica", "/api/consulta-medica/*",
                                "/api/historia-clinica", "/api/historia-clinica/*",
                                "/api/recetas", "/api/recetas/*",
                                "/api/detalle-receta", "/api/detalle-receta/*",
                                "/api/archivos", "/api/archivos/*",
                                "/api/medicamentos", "/api/medicamentos/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA", "PACIENTE")
                        .requestMatchers(HttpMethod.POST,
                                "/api/consulta-medica", "/api/historia-clinica", "/api/recetas", "/api/detalle-receta",
                                "/api/archivos", "/api/medicamentos")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/consulta-medica/*", "/api/historia-clinica/*", "/api/recetas/*",
                                "/api/detalle-receta/*", "/api/archivos/*", "/api/medicamentos/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/consulta-medica/*", "/api/historia-clinica/*", "/api/recetas/*",
                                "/api/detalle-receta/*", "/api/archivos/*", "/api/medicamentos/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA")

                        // Facturas: solo personal administrativo
                        .requestMatchers("/api/facturas", "/api/facturas/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA")

                        // Reportes: lectura solo para administración
                        .requestMatchers(HttpMethod.GET,
                                "/api/reportes/ingresos",
                                "/api/reportes/citas",
                                "/api/reportes/pacientes",
                                "/api/reportes/medicos")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
