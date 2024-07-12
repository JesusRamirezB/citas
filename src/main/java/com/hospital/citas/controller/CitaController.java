package com.hospital.citas.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.Cita;
import com.hospital.citas.service.CitaService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    @Autowired
    private CitaService citaService;

    // Endpoint para agendar una nueva cita
    @PostMapping("/agendar")
    public ResponseEntity<Cita> agendarCita(@RequestBody Cita cita) {
        Cita nuevaCita = citaService.agendarCita(cita);
        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }

    // Endpoint para obtener citas por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Cita>> obtenerCitasPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        List<Cita> citas = citaService.obtenerCitasPorFecha(fecha);
        return ResponseEntity.ok(citas);
    }

    // Endpoint para obtener citas por consultorio
    @GetMapping("/consultorio/{consultorioId}")
    public ResponseEntity<List<Cita>> obtenerCitasPorConsultorio(@PathVariable int consultorioId) {
        List<Cita> citas = citaService.obtenerCitasPorConsultorio(consultorioId);
        return ResponseEntity.ok(citas);
    }

    // Endpoint para obtener citas por doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Cita>> obtenerCitasPorDoctor(@PathVariable int doctorId) {
        List<Cita> citas = citaService.obtenerCitasPorDoctor(doctorId);
        return ResponseEntity.ok(citas);
    }

    // Endpoint para cancelar una cita
    @DeleteMapping("/{citaId}")
    public ResponseEntity<Void> cancelarCita(@PathVariable int citaId) {
        citaService.cancelarCita(citaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para editar una cita
    @PutMapping("/{citaId}")
    public ResponseEntity<Cita> editarCita(@PathVariable int citaId, @RequestBody Cita citaActualizada) {
        Cita citaActualizadaPersistida = citaService.editarCita(citaId, citaActualizada);
        return ResponseEntity.ok(citaActualizadaPersistida);
    }
}