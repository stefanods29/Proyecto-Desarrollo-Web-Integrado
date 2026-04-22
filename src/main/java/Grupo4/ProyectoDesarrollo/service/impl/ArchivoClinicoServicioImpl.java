package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.ArchivoClinico;
import Grupo4.ProyectoDesarrollo.repository.ArchivoClinicoRepository;
import Grupo4.ProyectoDesarrollo.service.ArchivoClinicoServicio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchivoClinicoServicioImpl implements ArchivoClinicoServicio {

    private final ArchivoClinicoRepository repository;

    public ArchivoClinicoServicioImpl(ArchivoClinicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ArchivoClinico> findAll() {
        return repository.findAll();
    }

    @Override
    public ArchivoClinico findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ArchivoClinico save(ArchivoClinico archivo) {
        return repository.save(archivo);
    }

    @Override
    public ArchivoClinico update(Long id, ArchivoClinico archivo) {
        ArchivoClinico existente = repository.findById(id).orElse(null);
        if (existente == null) return null;

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
        repository.deleteById(id);

    }
}
