package com.hospital.citas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.citas.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    int countByConsultorioIdAndHorario(int consultorioId, LocalDateTime horario);
    int countByDoctorIdAndHorario(int doctorId, LocalDateTime horario);

    // Método para buscar citas por nombre de paciente y horario entre dos fechas
    List<Cita> findByNombrePacienteAndHorarioBetween(String nombrePaciente, LocalDateTime inicio, LocalDateTime fin);

    // Métodos para contar citas según doctor y horario entre dos fechas
    int countByDoctorIdAndHorarioBetween(int doctorId, LocalDateTime inicio, LocalDateTime fin);

    // Métodos para buscar citas por consultorio, horario y doctor
    List<Cita> findByConsultorioId(int consultorioId);
    List<Cita> findByHorarioBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Cita> findByDoctorId(int doctorId);


}
