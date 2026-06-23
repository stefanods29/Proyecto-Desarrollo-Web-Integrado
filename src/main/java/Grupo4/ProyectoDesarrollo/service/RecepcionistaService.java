package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.dto.RecepcionistaDTO;
import java.util.List;

public interface RecepcionistaService {
    List<RecepcionistaDTO> findAll();
    RecepcionistaDTO findById(Long id);
    RecepcionistaDTO save(RecepcionistaDTO dto);
    RecepcionistaDTO update(Long id, RecepcionistaDTO dto);
    void delete(Long id);
    List<RecepcionistaDTO> findByClinica(Long clinicaId);
    RecepcionistaDTO findByUsername(String username);
}