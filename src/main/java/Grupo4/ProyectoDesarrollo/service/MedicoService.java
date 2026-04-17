package Grupo4.ProyectoDesarrollo.service;

import java.util.List;
import Grupo4.ProyectoDesarrollo.dto.medicoDTO;

public interface MedicoService {

    List<medicoDTO> listar();
    medicoDTO obtener(Long id);
    medicoDTO crear(medicoDTO medico);
    medicoDTO actualizar(Long id, medicoDTO medico);
    void eliminar(Long id);
    medicoDTO buscarPorId(Long id);
    medicoDTO guardar(medicoDTO medicoDTO);
}