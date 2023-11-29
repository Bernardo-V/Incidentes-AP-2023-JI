package com.argentinaprogramaapp.tpji2023.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.argentinaprograma.app.tpji2023.modelo.Incidente;
import com.argentinaprograma.app.tpji2023.modelo.Problema;
import com.argentinaprograma.app.tpji2023.repository.IncidenteRepository;

public class IncidenteService {
	@Autowired
    private IncidenteRepository incidenteRepository;


    public void agregarProblemaAIncidente(Incidente incidente, Problema nuevoProblema) {
        incidente.getProblemas().add(nuevoProblema);
        nuevoProblema.setIncidente(incidente);

        IncidenteRepository.save(incidente);
    }
}