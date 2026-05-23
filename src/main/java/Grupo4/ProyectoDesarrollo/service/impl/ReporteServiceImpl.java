package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Paciente;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.FacturaEstado;
import Grupo4.ProyectoDesarrollo.model.enums.Genero;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import Grupo4.ProyectoDesarrollo.repository.ConsultaMedicaRepository;
import Grupo4.ProyectoDesarrollo.repository.FacturaRepository;
import Grupo4.ProyectoDesarrollo.repository.PacienteRepository;
import Grupo4.ProyectoDesarrollo.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final FacturaRepository facturaRepository;
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaMedicaRepository consultaMedicaRepository;

    @Override
    public String reporteIngresos() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioMes = ahora.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime inicioHistorico = LocalDateTime.of(2000, 1, 1, 0, 0);

        BigDecimal totalMes = facturaRepository.sumTotalFacturadoPorEstadoYFechas(FacturaEstado.PAGADA, inicioMes, ahora);
        BigDecimal totalHistorico = facturaRepository.sumTotalFacturadoPorEstadoYFechas(FacturaEstado.PAGADA, inicioHistorico, ahora);

        return String.format(java.util.Locale.US, "Reporte de Ingresos:\n- Total Histórico: S/. %.2f\n- Total de este mes: S/. %.2f", 
                totalHistorico, totalMes);
    }

    @Override
    public String reporteCitas() {
        StringBuilder sb = new StringBuilder("Reporte de Citas por Estado:\n");
        for (CitaEstado estado : CitaEstado.values()) {
            long count = citaRepository.countByEstado(estado);
            sb.append("- ").append(estado).append(": ").append(count).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public String reportePacientes() {
        long totalPacientes = pacienteRepository.count();
        List<Paciente> pacientes = pacienteRepository.findAll();
        
        long masculinos = pacientes.stream().filter(p -> p.getGenero() == Genero.MASCULINO).count();
        long femeninos = pacientes.stream().filter(p -> p.getGenero() == Genero.FEMENINO).count();
        long otros = pacientes.stream().filter(p -> p.getGenero() == Genero.OTRO).count();

        return String.format("Reporte de Pacientes:\n- Total Registrados: %d\n- Masculinos: %d\n- Femeninos: %d\n- Otros: %d", 
                totalPacientes, masculinos, femeninos, otros);
    }

    @Override
    public String reporteMedicos() {
        List<Object[]> ranking = consultaMedicaRepository.obtenerRankingMedicosPorConsultas();
        if (ranking.isEmpty()) {
            return "No hay consultas médicas registradas para generar el ranking de médicos.";
        }

        StringBuilder sb = new StringBuilder("Ranking de Médicos por Consultas Realizadas:\n");
        int pos = 1;
        for (Object[] row : ranking) {
            sb.append(pos).append(". Dr. ").append(row[1]).append(" ").append(row[2])
              .append(" (ID: ").append(row[0]).append(") - Consultas: ").append(row[3]).append("\n");
            pos++;
        }
        return sb.toString().trim();
    }
}