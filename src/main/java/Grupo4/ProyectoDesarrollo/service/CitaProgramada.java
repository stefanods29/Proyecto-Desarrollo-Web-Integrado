package Grupo4.ProyectoDesarrollo.service;

import Grupo4.ProyectoDesarrollo.model.Cita;
import Grupo4.ProyectoDesarrollo.model.enums.CitaEstado;
import Grupo4.ProyectoDesarrollo.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CitaProgramada {
    @Autowired
    private CitaRepository citaRepository;

    @Scheduled(cron = "0 * * * * *")
    public void actualizarCitasNoAsistidas() {
        LocalDateTime ahora = LocalDateTime.now();

        // Buscamos todas las citas PENDIENTES cuya fecha y hora es menor a la actual
        List<Cita> citasVencidas = citaRepository.findByEstadoAndFechaHoraBefore(CitaEstado.PENDIENTE, ahora);

        if (!citasVencidas.isEmpty()) {
            for (Cita cita : citasVencidas) {
                cita.setEstado(CitaEstado.NO_ASISTIO);
                citaRepository.save(cita);
            }
            System.out.println("Se actualizaron " + citasVencidas.size() + " citas a estado NO_ASISTIO.");
        }
    }
}
