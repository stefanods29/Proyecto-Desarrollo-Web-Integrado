package Grupo4.ProyectoDesarrollo.controller;

import Grupo4.ProyectoDesarrollo.dto.HorarioMedicoDTO;
import Grupo4.ProyectoDesarrollo.model.Clinica;
import Grupo4.ProyectoDesarrollo.model.HorarioMedico;
import Grupo4.ProyectoDesarrollo.model.Medico;
import Grupo4.ProyectoDesarrollo.repository.HorarioMedicoRepository;
import Grupo4.ProyectoDesarrollo.service.ClinicaService;
import Grupo4.ProyectoDesarrollo.service.HorarioMedicoService;
import Grupo4.ProyectoDesarrollo.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioMedicoController {

    private final HorarioMedicoService service;
    private final MedicoService medicoService;
    private final ClinicaService clinicaService;
    private final HorarioMedicoRepository horarioMedicoRepository;

    @PostMapping
    public HorarioMedicoDTO crear(@RequestBody HorarioMedicoDTO dto) {
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        HorarioMedico horario = dto.toEntity(medico, clinica);
        HorarioMedico guardado = service.crear(horario);
        return HorarioMedicoDTO.fromEntity(guardado);
    }

    @GetMapping
    public List<HorarioMedicoDTO> listar() {
        return service.listar().stream()
                .map(HorarioMedicoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HorarioMedicoDTO buscarPorId(@PathVariable Long id) {
        return HorarioMedicoDTO.fromEntity(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public HorarioMedicoDTO actualizar(@PathVariable Long id,
                                     @RequestBody HorarioMedicoDTO dto) {
        dto.setId(id);
        Medico medico = dto.getMedicoId() != null ? medicoService.buscarPorId(dto.getMedicoId()) : null;
        Clinica clinica = dto.getClinicaId() != null ? clinicaService.buscarPorId(dto.getClinicaId()) : null;
        HorarioMedico horario = dto.toEntity(medico, clinica);
        HorarioMedico guardado = service.crear(horario);
        return HorarioMedicoDTO.fromEntity(guardado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/medico/{medicoId}")
    public List<HorarioMedicoDTO> obtenerPorMedico(@PathVariable Long medicoId) {
        return horarioMedicoRepository.findByMedicoId(medicoId).stream()
                .map(HorarioMedicoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/medico/{medicoId}/dia/{diaSemana}")
    public List<HorarioMedicoDTO> obtenerPorMedicoYDia(
            @PathVariable Long medicoId,
            @PathVariable java.time.DayOfWeek diaSemana) {
        return horarioMedicoRepository.findByMedicoIdAndDiaSemana(medicoId, diaSemana).stream()
                .map(HorarioMedicoDTO::fromEntity)
                .collect(Collectors.toList());
    }
}