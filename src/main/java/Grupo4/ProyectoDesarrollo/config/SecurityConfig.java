package Grupo4.ProyectoDesarrollo.config;

import java.util.Arrays;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import Grupo4.ProyectoDesarrollo.security.CustomAccessDeniedHandler;
import Grupo4.ProyectoDesarrollo.security.CustomAuthenticationEntryPoint;
import Grupo4.ProyectoDesarrollo.security.CustomUserDetailsService;
import Grupo4.ProyectoDesarrollo.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
                // --- ACTIVACIÓN DEL CORS ---
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Autenticación
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/me", "/api/auth/logout").authenticated()

                        // Clínicas
                        .requestMatchers(HttpMethod.GET, "/api/clinicas", "/api/clinicas/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clinicas").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/clinicas/*").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clinicas/*").hasRole("SUPER_ADMIN")

                        // Usuarios
                        .requestMatchers("/api/usuarios", "/api/usuarios/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")

                        // 🔥 NUEVO: Recepcionistas (Gestión y Perfil)
                        .requestMatchers("/api/recepcionistas", "/api/recepcionistas/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA")

                        // 🔓 Permitir que el Paciente consulte su propio perfil
                        .requestMatchers(HttpMethod.GET, "/api/pacientes/perfil")
                        .hasAnyRole("PACIENTE", "SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO", "ENFERMERA")

                        // Protección estricta para el resto de rutas de administración de pacientes
                        .requestMatchers(HttpMethod.GET, "/api/pacientes", "/api/pacientes/*", "/api/pacientes/buscar")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "ENFERMERA", "MEDICO")
                        .requestMatchers(HttpMethod.POST, "/api/pacientes")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA")
                        .requestMatchers(HttpMethod.PUT, "/api/pacientes/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "ENFERMERA", "MEDICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/pacientes/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA")

                        // Catálogos médicos
                        .requestMatchers(HttpMethod.GET,
                                "/api/medicos", "/api/medicos/*", "/api/especialidades", "/api/especialidades/*",
                                "/api/consultorios", "/api/consultorios/*", "/api/horarios", "/api/horarios/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "RECEPCIONISTA")
                        .requestMatchers(HttpMethod.POST,
                                "/api/medicos", "/api/especialidades", "/api/consultorios", "/api/horarios")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/medicos/*", "/api/especialidades/*", "/api/consultorios/*", "/api/horarios/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/medicos/*", "/api/especialidades/*", "/api/consultorios/*", "/api/horarios/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")

                        // 🔓 CORRECCIÓN AQUÍ: Permitir que el paciente vea sus propias citas
                        .requestMatchers(HttpMethod.GET, "/api/citas/mis-citas")
                        .hasAnyRole("PACIENTE", "SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO")

                        // Citas (Protección general)
                        .requestMatchers("/api/citas", "/api/citas/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA")

                        // Expedientes y recetas
                        .requestMatchers(HttpMethod.GET,
                                "/api/consulta-medica", "/api/consulta-medica/*", "/api/historia-clinica",
                                "/api/historia-clinica/*",
                                "/api/recetas", "/api/recetas/*", "/api/detalle-receta", "/api/detalle-receta/*",
                                "/api/archivos", "/api/archivos/*", "/api/medicamentos", "/api/medicamentos/*")
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

                        // Facturas
                        .requestMatchers("/api/facturas", "/api/facturas/*", "/api/detalle-factura",
                                "/api/detalle-factura/*")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "PERSONAL_ADMINISTRATIVO", "RECEPCIONISTA")

                        // Reportes
                        .requestMatchers(HttpMethod.GET,
                                "/api/reportes/ingresos", "/api/reportes/citas", "/api/reportes/pacientes",
                                "/api/reportes/medicos")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // --- CONFIGURACIÓN CORS ---
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:4200", "https://tu-futuro-dominio-vercel.vercel.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","PATCH" , "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}