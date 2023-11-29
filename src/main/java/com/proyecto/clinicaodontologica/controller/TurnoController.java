package com.proyecto.clinicaodontologica.controller;
import com.proyecto.clinicaodontologica.dto.TurnoDTO;
import com.proyecto.clinicaodontologica.entity.Odontologo;
import com.proyecto.clinicaodontologica.entity.Paciente;
import com.proyecto.clinicaodontologica.entity.Turno;
import com.proyecto.clinicaodontologica.service.OdontologoService;
import com.proyecto.clinicaodontologica.service.PacienteService;
import com.proyecto.clinicaodontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarTurno(@RequestBody Turno turno) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(turno.getPacienteId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologoId());

        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            turnoService.guardarTurnoConDetalles(turno);
            return ResponseEntity.ok("Turno guardado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("Error al crear el turno.");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> consultarTurnoPorId(@PathVariable Long id) {
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);

        if (turnoBuscado.isPresent()) {
            TurnoDTO turnoDTO = turnoService.turnoATurnoDTOConDetalles(turnoBuscado.get());
            return ResponseEntity.ok(turnoDTO);
        } else {
            return ResponseEntity.badRequest().body("Ha ocurrido un error.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) {
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);

        if (turnoBuscado.isPresent()) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("Error al intentar eliminar el turno.");
        }
    }
}