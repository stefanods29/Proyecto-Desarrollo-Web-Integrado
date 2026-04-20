package Grupo4.ProyectoDesarrollo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import Grupo4.ProyectoDesarrollo.dto.medicoDTO;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.repository.MedicoRepository;
import Grupo4.ProyectoDesarrollo.service.MedicoService;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    @Override
    public List<medicoDTO> listar() {
        return medicoRepository.findAll()
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public medicoDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        return convertirA_DTO(medico);
    }

    @Override
    public medicoDTO guardar(medicoDTO medicoDTO) {

        Medico medicoEntity = convertirA_Entidad(medicoDTO);

        Medico medicoGuardado = medicoRepository.save(medicoEntity);

        return convertirA_DTO(medicoGuardado);
    }

    @Override
    public medicoDTO actualizar(Long id, medicoDTO medicoDTO) {

        Medico medicoExistente = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        medicoExistente.setNombre(medicoDTO.getNombre());
        medicoExistente.setEspecialidad(medicoDTO.getEspecialidad());
        medicoExistente.setCorreo(medicoDTO.getCorreo());
        medicoExistente.setTelefono(medicoDTO.getTelefono());

        Medico medicoActualizado = medicoRepository.save(medicoExistente);

        return convertirA_DTO(medicoActualizado);
    }

    @Override
    public void eliminar(Long id) {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        medicoRepository.delete(medico);
    }

    private medicoDTO convertirA_DTO(Medico medico) {
        medicoDTO dto = new medicoDTO();
        dto.setId(medico.getId());
        dto.setNombre(medico.getNombre());
        dto.setEspecialidad(medico.getEspecialidad());
        dto.setTelefono(medico.getTelefono());
        dto.setCorreo(medico.getCorreo());
        return dto;
    }

    private Medico convertirA_Entidad(medicoDTO dto) {
        Medico medico = new Medico();
        medico.setId(dto.getId());
        medico.setNombre(dto.getNombre());
        medico.setEspecialidad(dto.getEspecialidad());
        medico.setTelefono(dto.getTelefono());
        medico.setCorreo(dto.getCorreo());
        return medico;
    }

    @Override
    public medicoDTO obtener(Long id) {
        return buscarPorId(id);
    }

    @Override
    public medicoDTO crear(medicoDTO medico) {
        return guardar(medico);
    }
}