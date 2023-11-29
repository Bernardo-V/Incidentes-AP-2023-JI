package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

@Entity
@AllArgsConstructor
@Data
@Table(name="incidente")
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_incidente")
    private int idIncidente;  
    private int idTipodeProblema;
    private Date fechaCreacion;
    private Date fechaInicio;
    private Date fechaFinEstimada;
    private Duration duracionEstimada; // Duración estimada como objeto Duration
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_contrato")
    private Contrato contrato;

    @OneToMany(mappedBy = "incidente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Problema> problemas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "id_detalle_servicio")
    private DetalleServicio detalleServicio ;

    // Constructor vacio
    public Incidente() {
        this.fechaCreacion = new Date();
    }

    public void setId_incidente(int idIncidente) {
        this.idIncidente = idIncidente;
    }

    public void setId_contrato(Contrato contrato) {
        this.contrato = contrato;
    }
        
    public void setId_tipo_de_problema(int idTipodeProblema) {
        this.idTipodeProblema = idTipodeProblema;
    }

    public void setId_tecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public void setFecha_creacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFecha_inicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFecha_fin_estimada(Date fechaFinEstimada) {
        this.fechaFinEstimada = fechaFinEstimada;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDuracion_estimada(Duration duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }


    public void setProblemas(List<Problema> problemas) {
        this.problemas = problemas;
    }


    // Método para determinar la fecha de inicio basada en la disponibilidad del técnico
    public void determinarFechaInicio(int idTecnico, List<Incidente> incidentesAsignadosAlTecnico) {
        Incidente ultimoIncidente = obtenerUltimoIncidenteDelTecnico(incidentesAsignadosAlTecnico);

        if (ultimoIncidente == null) {
            // Si el técnico no tiene incidentes asignados, la fecha de inicio es la fecha de creación
            this.fechaInicio = this.fechaCreacion;
        } else {
            // Si el técnico tiene incidentes asignados, calcular un día después de la fecha_fin_estimada del último incidente
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ultimoIncidente.getFechaFinEstimada());
            calendar.add(Calendar.DATE, 1); // Añadir un día
            this.fechaInicio = calendar.getTime();
        }
    }

    // Método para obtener el incidente con la fecha_fin_estimada más lejana entre los que no están resueltos
    private Incidente obtenerUltimoIncidenteDelTecnico(List<Incidente> incidentesAsignadosAlTecnico) {
        Incidente incidenteMasTardio = null;

        for (Incidente incidente : incidentesAsignadosAlTecnico) {
            if (incidente.getFechaFinEstimada() != null && !incidente.getEstado().equalsIgnoreCase("resuelto")) {
                // Si es el primer incidente que estamos revisando o si la fecha_fin_estimada es más lejana que la del incidente más tardío registrado
                if (incidenteMasTardio == null || incidente.getFechaFinEstimada().after(incidenteMasTardio.getFechaFinEstimada())) {
                    incidenteMasTardio = incidente;
                }
            }
        }

        return incidenteMasTardio;
    }

    // Método para calcular la duración estimada del incidente
    // sumando las duraciones de los problemas en una colección de problemas
    public Duration calcularDuracionEstimada() {
        Duration duracionTotal = Duration.ZERO;
        for (Problema problema : problemas) {
            duracionTotal = duracionTotal.plus(problema.getDuracion_problema());
        }
        return duracionTotal;
    }


    public String calcularEstado() {
        Date fechaActual = new Date();
        boolean todosProblemasResueltos = problemas.stream().allMatch(problema -> problema.getEstado().equalsIgnoreCase("finalizado"));

        if (fechaInicio != null && fechaInicio.after(fechaActual)) {
            return "pendiente";
        } else if (fechaInicio != null && fechaInicio.before(fechaActual) && (fechaFinEstimada == null || fechaFinEstimada.after(fechaActual))) {
            return "en proceso";
        } else if (fechaFinEstimada != null && fechaFinEstimada.before(fechaActual) && !todosProblemasResueltos) {
            return "demorado";
        } else if (todosProblemasResueltos) {
            return "resuelto";
        } else {
            return "pendiente"; // O cualquier otro estado por defecto
        }
    }


    public Date obtenerFechaCierre() {
        // Verificar si todos los problemas están resueltos
        boolean todosProblemasResueltos = problemas.stream().allMatch(problema -> problema.getEstado().equalsIgnoreCase("finalizado"));

        if (todosProblemasResueltos) {
            // Encontrar la fecha de cierre más reciente entre los problemas resueltos
            Date fechaCierreMasReciente = null;
            for (Problema problema : problemas) {
                if (problema.getEstado().equalsIgnoreCase("finalizado") && problema.getFecha_cierre() != null) {
                    if (fechaCierreMasReciente == null || problema.getFecha_cierre().after(fechaCierreMasReciente)) {
                        fechaCierreMasReciente = problema.getFecha_cierre();
                    }
                }
            }
            return fechaCierreMasReciente;
        }
        return null; 
    }
    
    
    //Devuelve una fecha calculada basada en una fecha de inicio y una duración estimada
    public Date obtenerFechaFinEstimada(int dias) {
        if (fechaInicio != null && duracionEstimada != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicio);
            calendar.add(Calendar.HOUR, (int)duracionEstimada.toHours()); // Agregar la duración estimada en horas
            return calendar.getTime();
        }
        return null; // Si no se cumplen las condiciones, devuelve null
    }
    

    // Método de notificación al cliente al crear el incidente
    public void notificarClienteFechaPosibleSolucion(Incidente incidente) {
    	Date fechaPosibleResolucion = this.getFechaFinEstimada();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("Incidente creado. Fecha posible de resolución: " + sdf.format(fechaPosibleResolucion));
    }


    public void notificarTecnico(Incidente incidente) {
    	System.out.println("Notificar al técnico sobre el incidente " + incidente.getIdIncidente());

    }

    public void notificarClienteEstadoResuelto(Incidente incidente) {
    	String estado = calcularEstado();

        if ("pendiente".equalsIgnoreCase(estado) || "en proceso".equalsIgnoreCase(estado) || "demorado".equalsIgnoreCase(estado)) {
            notificarTecnico(incidente);
            // Luego, notifica al cliente sobre el estado y la fecha estimada
            notificarClienteFechaPosibleSolucion(incidente);
        } else if ("resuelto".equalsIgnoreCase(estado)) {
            // El incidente ya está resuelto, envía un mensaje específico
            enviarMensajeResuelto();
        }
    }
    
    private void enviarMensajeResuelto() {
        // para notificar al cliente que el incidente está resuelto
        System.out.println("El incidente ha sido resuelto. Gracias por su paciencia.");
 
    }

    // Método para obtener incidentes por fecha en una lista dada
    public static List<Incidente> obtenerIncidentesPorFechaEnLista(Date fecha, List<Incidente> listaIncidentes) {
        List<Incidente> incidentesPorFecha = new ArrayList<>();

        for (Incidente incidente : listaIncidentes) {
            if (incidente.getFechaCreacion() != null && incidente.getFechaCreacion().equals(fecha)) {
                incidentesPorFecha.add(incidente);
            }
        }

        return incidentesPorFecha;
    }


    // Método para obtener el técnico con más incidentes resueltos en los últimos N días
    public Tecnico obtenerTecnicoMasIncidentesResueltos(int dias, List<Incidente> listaIncidentes) {
    	// Obtener la fecha actual y calcular la fecha límite hace N días
        Date fechaLimite = obtenerFechaFinEstimada(dias);

        // Filtar los incidentes resueltos en el rango de días especificado
        List<Incidente> incidentesResueltos = listaIncidentes.stream()
                .filter(incidente -> "resuelto".equalsIgnoreCase(incidente.calcularEstado()) && incidente.getFechaCreacion().after(fechaLimite))
                .collect(Collectors.toList());

        // Contar la cantidad de incidentes resueltos por cada técnico
        Map<Tecnico, Long> conteoPorTecnico = incidentesResueltos.stream()
                .collect(Collectors.groupingBy(Incidente::getTecnico, Collectors.counting()));

        // Encontrar al técnico con más incidentes resueltos
        Optional<Map.Entry<Tecnico, Long>> tecnicoMasIncidentes = conteoPorTecnico.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        // Devolver al técnico con más incidentes resueltos
        return tecnicoMasIncidentes.map(Map.Entry::getKey).orElse(null);
    }
    
    
    public Tecnico obtenerTecnicoMasIncidentesResueltosEspecialidad(int dias, int idEspecialidad, List<Incidente> listaIncidentes) {
        // Obtener la fecha actual y calcular la fecha límite hace N días
        Date fechaLimite = obtenerFechaFinEstimada(dias);

        // Filtar los incidentes resueltos en el rango de días especificado y de la especialidad dada
        List<Incidente> incidentesResueltosEspecialidad = listaIncidentes.stream()
                .filter(incidente -> "resuelto".equalsIgnoreCase(incidente.calcularEstado()) &&
                        incidente.getFechaCreacion().after(fechaLimite) &&
                        incidente.getTecnico().getEspecialidades().stream().anyMatch(especialidad -> especialidad.getIdEspecialidad() == idEspecialidad))
                .collect(Collectors.toList());

        // Contar la cantidad de incidentes resueltos por cada técnico
        Map<Tecnico, Long> conteoPorTecnico = incidentesResueltosEspecialidad.stream()
                .collect(Collectors.groupingBy(Incidente::getTecnico, Collectors.counting()));

        // Encontrar al técnico con más incidentes resueltos
        Optional<Map.Entry<Tecnico, Long>> tecnicoMasIncidentes = conteoPorTecnico.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        // Devolver al técnico con más incidentes resueltos
        return tecnicoMasIncidentes.map(Map.Entry::getKey).orElse(null);
    }

    
  
    public Tecnico obtenerTecnicoMasRapido(List<Incidente> incidentes) {
        // Ordenar los incidentes por duración estimada en orden ascendente
        List<Incidente> incidentesOrdenados = incidentes.stream()
                .filter(incidente -> "resuelto".equalsIgnoreCase(incidente.calcularEstado()))
                .sorted(Comparator.comparing(Incidente::calcularDuracionEstimada))
                .collect(Collectors.toList());

        // Obtener al técnico asociado al primer incidente (el más rápido)
        return incidentesOrdenados.isEmpty() ? null : incidentesOrdenados.get(0).getTecnico();
    }


}

