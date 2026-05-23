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
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/clinicas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/clinicas/**").hasRole("SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/clinicas/**").hasRole("SUPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/clinicas/**").hasRole("SUPER_ADMIN")
                
                .requestMatchers("/api/usuarios/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                
                .requestMatchers("/api/pacientes/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO", "ENFERMERA", "PACIENTE")
                
                .requestMatchers(HttpMethod.GET, "/api/medicos/**", "/api/especialidades/**", "/api/consultorios/**", "/api/horarios/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "RECEPCIONISTA", "ENFERMERA")
                .requestMatchers(HttpMethod.POST, "/api/medicos/**", "/api/especialidades/**", "/api/consultorios/**", "/api/horarios/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                .requestMatchers(HttpMethod.PUT, "/api/medicos/**", "/api/especialidades/**", "/api/consultorios/**", "/api/horarios/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                .requestMatchers(HttpMethod.DELETE, "/api/medicos/**", "/api/especialidades/**", "/api/consultorios/**", "/api/horarios/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                
                .requestMatchers("/api/citas/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "MEDICO", "PACIENTE")
                
                .requestMatchers(HttpMethod.GET, "/api/consulta-medica/**", "/api/historia-clinica/**", "/api/recetas/**", "/api/detalle-receta/**", "/api/archivos/**", "/api/medicamentos/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA", "PACIENTE")
                .requestMatchers(HttpMethod.POST, "/api/consulta-medica/**", "/api/historia-clinica/**", "/api/recetas/**", "/api/detalle-receta/**", "/api/archivos/**", "/api/medicamentos/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA")
                .requestMatchers(HttpMethod.PUT, "/api/consulta-medica/**", "/api/historia-clinica/**", "/api/recetas/**", "/api/detalle-receta/**", "/api/archivos/**", "/api/medicamentos/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA")
                .requestMatchers(HttpMethod.DELETE, "/api/consulta-medica/**", "/api/historia-clinica/**", "/api/recetas/**", "/api/detalle-receta/**", "/api/archivos/**", "/api/medicamentos/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "MEDICO", "ENFERMERA")
                
                .requestMatchers("/api/facturas/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA", "RECEPCIONISTA", "PACIENTE")
                
                .requestMatchers("/api/reportes/**").hasAnyRole("SUPER_ADMIN", "ADMIN_CLINICA")
                
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
