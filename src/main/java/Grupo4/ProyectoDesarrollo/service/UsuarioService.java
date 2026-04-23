package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario crear(Usuario usuario);
    List<Usuario> listar();
    Usuario buscarPorId(Long id);
    void eliminar(Long id);
}
