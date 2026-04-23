package Grupo4.ProyectoDesarrollo.service.impl;

import Grupo4.ProyectoDesarrollo.model.Medicamento;
import Grupo4.ProyectoDesarrollo.repository.MedicamentoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicamentoServicioImplTest {

    @Mock
    private MedicamentoRepository repository;

    @InjectMocks
    private MedicamentoServicioImpl servicio;

    private Medicamento medicamento;

    @BeforeEach
    void setUp(){
        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNombreComercial("Panadol");
        medicamento.setNombreGenerico("Paracetamol");
        medicamento.setPresentacion("Tableta");
        medicamento.setConcentracion("500mg");
        medicamento.setViaAdministracion("Oral");
        medicamento.setActivo(true);
    }

    @Test
    void findAll(){
        when(repository.findAll()).thenReturn(List.of(medicamento));

        List<Medicamento> resultado = servicio.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void findById(){
        when(repository.findById(1L)).thenReturn(Optional.of(medicamento));

        Medicamento resultado = servicio.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repository).findById(1L);
    }

    @Test
    void save(){
        when(repository.save(medicamento)).thenReturn(medicamento);

        Medicamento resultado = servicio.save(medicamento);

        assertNotNull(resultado);
        assertEquals("Panadol", resultado.getNombreComercial());
        verify(repository).save(medicamento);
    }

    @Test
    void update(){
        when(repository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(repository.save(any(Medicamento.class))).thenReturn(medicamento);

        Medicamento nuevo = new Medicamento();
        nuevo.setNombreComercial("Ibuprofeno");
        nuevo.setNombreGenerico("Ibuprofeno");
        nuevo.setPresentacion("Capsula");
        nuevo.setConcentracion("400mg");
        nuevo.setViaAdministracion("Oral");
        nuevo.setActivo(true);

        Medicamento resultado = servicio.update(1L, nuevo);

        assertNotNull(resultado);
        verify(repository).findById(1L);
        verify(repository).save(any(Medicamento.class));


    }

    @Test
    void delete(){
        doNothing().when(repository).deleteById(1L);

        servicio.delete(1L);

        verify(repository).deleteById(1L);
    }
}
