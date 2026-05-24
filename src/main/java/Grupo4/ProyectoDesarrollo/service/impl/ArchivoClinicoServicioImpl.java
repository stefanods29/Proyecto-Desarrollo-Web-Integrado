package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.exception.ResourceNotFoundException;
import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.repository.ArchivoClinicoRepository;
import Grupo4.ProyectoDesarrollo.service.ArchivoClinicoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivoClinicoServicioImpl implements ArchivoClinicoServicio {

    private final ArchivoClinicoRepository repository;

    @Override
    public List<ArchivoClinico> findAll() {
        return repository.findAll();
    }

    @Override
    public ArchivoClinico findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Archivo clínico no encontrado con id: " + id));
    }

    @Override
    public ArchivoClinico save(ArchivoClinico archivo) {
        return repository.save(archivo);
    }

    @Override
    public ArchivoClinico update(Long id, ArchivoClinico archivo) {
        ArchivoClinico existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Archivo clínico no encontrado con id: " + id));

        existente.setConsultaMedica(archivo.getConsultaMedica());
        existente.setNombreArchivo(archivo.getNombreArchivo());
        existente.setTipoArchivo(archivo.getTipoArchivo());
        existente.setRutaArchivo(archivo.getRutaArchivo());
        existente.setDescripcion(archivo.getDescripcion());
        existente.setFechaSubida(archivo.getFechaSubida());

        return repository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Archivo clínico no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
