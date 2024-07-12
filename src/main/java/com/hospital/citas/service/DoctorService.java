package com.hospital.citas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.citas.model.Doctor;
import com.hospital.citas.repository.DoctorRepository;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor agregarDoctor(Doctor doctor) {
        try {
            return doctorRepository.save(doctor);
        } catch (Exception e) {
            throw new RuntimeException("Error agregando el doctor: " + e.getMessage());
        }
    }

    public List<Doctor> obtenerTodosLosDoctores() {
        try {
            return doctorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los doctores: " + e.getMessage());
        }
    }

    public Doctor actualizarDoctor(int id, Doctor doctorActualizado) {
        try {
            Optional<Doctor> doctorExistente = doctorRepository.findById(id);
            if (doctorExistente.isPresent()) {
                Doctor doctor = doctorExistente.get();
                doctor.setNombre(doctorActualizado.getNombre());
                doctor.setApellidoPaterno(doctorActualizado.getApellidoPaterno());
                doctor.setApellidoMaterno(doctorActualizado.getApellidoMaterno());
                doctor.setEspecialidad(doctorActualizado.getEspecialidad());
                return doctorRepository.save(doctor);
            } else {
                throw new RuntimeException("No se encontró el doctor con ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el doctor con ID: " + id + ": " + e.getMessage());
        }
    }
}
