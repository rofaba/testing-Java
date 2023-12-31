package com.proyecto.clinicaodontologica.service;
import com.proyecto.clinicaodontologica.entity.Domicilio;
import com.proyecto.clinicaodontologica.repository.PacienteRepository;
import com.proyecto.clinicaodontologica.entity.Paciente;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DomicilioService domicilioService;

    public Paciente registrarPaciente(Paciente paciente) {
        Domicilio domicilioGuardado = domicilioService.guardarDomicilio(paciente.getDomicilio());
        paciente.setDomicilio(domicilioGuardado);
        return pacienteRepository.save(paciente);
    }


    public void actualizarPaciente(Paciente paciente){

        pacienteRepository.save(paciente);
    }
    public void eliminarPaciente(Long id){

        pacienteRepository.deleteById(id);
    }

    public Optional<Paciente> buscarPacientePorID(Long id){

        return pacienteRepository.findById(id);
    }
    public Optional<Paciente> buscarEmail(String email){
        return pacienteRepository.findByEmail(email);
    }
    public List<Paciente> listarTodos(){

        return pacienteRepository.findAll();
    }
}
