package com.argentinaprogramaapp.tpji2023.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.argentinaprograma.app.tpji2023.modelo.Incidente;
import com.argentinaprograma.app.tpji2023.modelo.Tecnico;

public class TecnicoService {

    public Tecnico obtenerTecnicoMasIncidentesResueltosEspecialidadEnUltimosNDias(
            int dias, int idEspecialidad, List<Incidente> listaIncidentes) {

        Date fechaLimite = calcularFechaLimite(dias);

        return listaIncidentes.stream()
                .filter(incidente -> incidente.getFechaFinEstimada() != null &&
                        incidente.getFechaFinEstimada().after(fechaLimite) &&
                        incidente.calcularEstado().equalsIgnoreCase("resuelto") &&
                        incidente.getTecnico() != null &&
                        tieneEspecialidad(incidente.getTecnico(), idEspecialidad))
                .collect(Collectors.groupingBy(Incidente::getTecnico,
                        Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(entry -> entry.getValue()))
                .map(entry -> entry.getKey())
                .orElse(null);
    }

    private boolean tieneEspecialidad(Tecnico tecnico, int idEspecialidad) {
        return tecnico.getEspecialidades().stream()
                .anyMatch(especialidad -> especialidad.getIdEspecialidad() == idEspecialidad);
    }

    private Date calcularFechaLimite(int dias) {
    	// Calcular la fecha de N días atrás desde la fecha actual
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.DAY_OF_YEAR, -dias);
        return calendar.getTime();
    }
}

