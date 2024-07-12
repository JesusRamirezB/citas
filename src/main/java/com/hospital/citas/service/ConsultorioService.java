package com.hospital.citas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.citas.model.Consultorio;
import com.hospital.citas.repository.ConsultorioRepository;

@Service
public class ConsultorioService {
    @Autowired
    private ConsultorioRepository consultorioRepository;

    public Consultorio agregarConsultorio(Consultorio consultorio) {
        try {
            return consultorioRepository.save(consultorio);
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar el consultorio: " + e.getMessage());
        }
    }

    public List<Consultorio> obtenerTodosLosConsultorios() {
        try {
            return consultorioRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los consultorios: " + e.getMessage());
        }
    }

    public Consultorio actualizaConsultorio(int id, Consultorio consultorioActualizado) {
        try {
            Optional<Consultorio> consultorioExistente = consultorioRepository.findById(id);
            if (consultorioExistente.isPresent()) {
                Consultorio consultorio = consultorioExistente.get();
                consultorio.setNumeroConsultorio(consultorioActualizado.getNumeroConsultorio());
                consultorio.setPiso(consultorioActualizado.getPiso());
                return consultorio;
            } else {
                throw new RuntimeException("No se encontro el consultorio con ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el consultorio con ID " + id + ": " + e.getMessage());
        }
    }
}
