package com.hospital.citas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.hospital.citas.model.Consultorio;
import com.hospital.citas.model.Doctor;
import com.hospital.citas.repository.ConsultorioRepository;
import com.hospital.citas.repository.DoctorRepository;

public class DatosPrueba implements CommandLineRunner {
    private final DoctorRepository doctorRepository;
    private final ConsultorioRepository consultorioRepository;
    
    @Autowired
    public DatosPrueba(DoctorRepository doctorRepository, ConsultorioRepository consultorioRepository){
        this.doctorRepository = doctorRepository;
        this.consultorioRepository = consultorioRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        Doctor doctor1 = new Doctor("Dr. Juan", "García", "López", "Medicina Interna");
        Doctor doctor2 = new Doctor("Dra. María", "Martínez", "Sánchez", "Cardiología");
        Doctor doctor3 = new Doctor("Dr. Carlos", "Pérez", "Ramírez", "Dermatología");
        Doctor doctor4 = new Doctor("Dra. Laura", "Gómez", "Jiménez", "Pediatría");
        Doctor doctor5 = new Doctor("Dr. Roberto", "Fernández", "Ruiz", "Oftalmología");

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);
        doctorRepository.save(doctor4);
        doctorRepository.save(doctor5);

        Consultorio consultorio1 = new Consultorio("101", 1);
        Consultorio consultorio2 = new Consultorio("202", 2);
        Consultorio consultorio3 = new Consultorio("303", 3);
        Consultorio consultorio4 = new Consultorio("404", 4);
        Consultorio consultorio5 = new Consultorio("505", 5);

        consultorioRepository.save(consultorio1);
        consultorioRepository.save(consultorio2);
        consultorioRepository.save(consultorio3);
        consultorioRepository.save(consultorio4);
        consultorioRepository.save(consultorio5);

    }
}
