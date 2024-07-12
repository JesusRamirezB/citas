package com.hospital.citas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.citas.model.Cita;
import com.hospital.citas.repository.CitaRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public Cita agendarCita(Cita cita) {
        try {
            validarCita(cita);
            return citaRepository.save(cita);
        } catch (Exception e) {
            throw new RuntimeException("Error al agendar la cita: " + e.getMessage());
        }
    }

    public List<Cita> obtenerCitasPorFecha(LocalDateTime fecha) {
        try {
            return citaRepository.findByHorarioBetween(fecha.withHour(0).withMinute(0), fecha.withHour(23).withMinute(59));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las citas por fecha: " + e.getMessage());
        }
    }

    public List<Cita> obtenerCitasPorConsultorio(int consultorioId) {
        try {
            return citaRepository.findByConsultorioId(consultorioId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las citas por consultorio: " + e.getMessage());
        }
    }

    public List<Cita> obtenerCitasPorDoctor(int doctorId) {
        try {
            // Implementa lógica para obtener citas por doctor
            return citaRepository.findByDoctorId(doctorId);
        } catch (Exception e) {
            // Manejo de excepciones
            throw new RuntimeException("Error al obtener las citas por doctor: " + e.getMessage());
        }
    }

    public void cancelarCita(int citaId) {
        try {
            citaRepository.deleteById(citaId);
        } catch (Exception e) {
            // Manejo de excepciones
            throw new RuntimeException("Error al cancelar la cita con ID " + citaId + ": " + e.getMessage());
        }
    }

    public Cita editarCita(int citaId, Cita citaActualizada) {
        try {
            Optional<Cita> citaExistente = citaRepository.findById(citaId);
            if (citaExistente.isPresent()) {
                Cita cita = citaExistente.get();
                cita.setConsultorioId(citaActualizada.getConsultorioId());
                cita.setDoctorId(citaActualizada.getDoctorId());
                cita.setHorario(citaActualizada.getHorario());
                cita.setNombrePaciente(citaActualizada.getNombrePaciente());
                
                validarCita(citaActualizada); // Realiza las validaciones antes de actualizar la cita
                
                return citaRepository.save(cita);
            } else {
                throw new RuntimeException("No se encontró la cita con ID: " + citaId);
            }
        } catch (Exception e) {
            // Manejo de excepciones
            throw new RuntimeException("Error al editar la cita con ID " + citaId + ": " + e.getMessage());
        }
    }



    private void validarCita(Cita cita) {
        // Validaciones personalizadas según los requisitos mencionados

        // 1. No se empalmen citas en el mismo consultorio a la misma hora
        if (citaRepository.countByConsultorioIdAndHorario(cita.getConsultorioId(), cita.getHorario()) > 0) {
            throw new IllegalArgumentException("Ya existe una cita en el mismo consultorio a la misma hora.");
        }

        // 2. No se empalmen citas para el mismo médico a la misma hora
        if (citaRepository.countByDoctorIdAndHorario(cita.getDoctorId(), cita.getHorario()) > 0) {
            throw new IllegalArgumentException("El médico ya tiene una cita a la misma hora.");
        }

        // 3. Las citas de los pacientes deben tener al menos 2 horas de diferencia
        LocalDateTime horaMinima = cita.getHorario().minusHours(2);
        LocalDateTime horaMaxima = cita.getHorario().plusHours(2);
        List<Cita> citasPaciente = citaRepository.findByNombrePacienteAndHorarioBetween(cita.getNombrePaciente(),
                horaMinima, horaMaxima);
        if (!citasPaciente.isEmpty()) {
            throw new IllegalArgumentException("El paciente ya tiene otra cita dentro de las 2 horas antes o después.");
        }

        // 4. El médico no puede tener más de 8 citas al día
        LocalDateTime inicioDia = cita.getHorario().toLocalDate().atStartOfDay();
        LocalDateTime finDia = cita.getHorario().toLocalDate().atTime(23, 59);
        if (citaRepository.countByDoctorIdAndHorarioBetween(cita.getDoctorId(), inicioDia, finDia) >= 8) {
            throw new IllegalArgumentException("El médico ya tiene 8 citas programadas para este día.");
        }
    }

}