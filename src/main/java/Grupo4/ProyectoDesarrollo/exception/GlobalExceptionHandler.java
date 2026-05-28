package Grupo4.ProyectoDesarrollo.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarErroresValidacion(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String mensajeError = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Error de validación");

        return crearErrorResponse(HttpStatus.BAD_REQUEST, mensajeError, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> manejarDuplicateResource(
            DuplicateResourceException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> manejarBusinessRule(
            BusinessRuleException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), request);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> manejarBadCredentials(
            BadCredentialsException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos", request);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarUsernameNotFound(
            UsernameNotFoundException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "Usuario no encontrado", request);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> manejarDisabledAccount(
            DisabledException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "La cuenta está deshabilitada", request);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ErrorResponse> manejarCredentialsExpired(
            CredentialsExpiredException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "Las credenciales han vencido. Por favor, actualice su contraseña.", request);
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> manejarExpiredJwtException(
            ExpiredJwtException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "El token ha expirado. Por favor, inicie sesión nuevamente.", request, "Token Expired");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> manejarSignatureException(
            SignatureException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "La firma del token no es válida", request, "Invalid Token Signature");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> manejarMalformedJwtException(
            MalformedJwtException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "El token proporcionado no tiene un formato válido", request, "Malformed Token");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> manejarUnsupportedJwtException(
            UnsupportedJwtException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "El token proporcionado no es compatible", request, "Unsupported Token");
    }


    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErrorResponse> manejarInsufficientAuthentication(
            InsufficientAuthenticationException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, "No se proporcionó un token de autenticación o es inválido.", request, "Authentication Required");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> manejarAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.FORBIDDEN, "Acceso denegado. No tiene permisos suficientes para este recurso.", request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> manejarAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage() != null ? ex.getMessage() : "Error de autenticación", request);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> manejarRuntime(
            RuntimeException ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage() != null ? ex.getMessage() : "Error interno del servidor", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExceptionGeneral(
            Exception ex, HttpServletRequest request) {
        return crearErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado", request);
    }


    private ResponseEntity<ErrorResponse> crearErrorResponse(HttpStatus status, String mensaje, HttpServletRequest request) {
        return crearErrorResponse(status, mensaje, request, status.getReasonPhrase());
    }

    private ResponseEntity<ErrorResponse> crearErrorResponse(HttpStatus status, String mensaje, HttpServletRequest request, String errorType) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(errorType)
                .message(mensaje)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, status);
    }
}