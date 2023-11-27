package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
@Data
//@ToString
@Table(name="incidente")
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_incidente;
    private int id_contrato;
    private int id_tipo_de_problema;
    private int id_tecnico;
    private Date fecha_creacion;
    private Date fecha_inicio;
    private Date fecha_fin_estimada;
    private Duration duracion_estimada; // Duración estimada como objeto Duration
    private String estado;
    private List<Problema> problemas;

    @ManyToOne
    @JoinColumn(name = "id_contrato")
    private Contrato contrato;

    @OneToMany(mappedBy = "id_problema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Problema> problema = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "id_detalle_servicio")
    private DetalleServicio detalleservicio;

    // Constructor vacío
    public Incidente() {
        this.fecha_creacion = new Date();
    }

    // Constructor con todos los atributos
    public Incidente(int id_incidente, int id_contrato, int id_tipo_de_problema, int id_tecnico, Date fecha_creacion, Date fecha_inicio, Date fecha_fin_estimada, Duration duracion_estimada, String estado, List<Problema> problemas) {
        this.id_incidente = id_incidente;
        this.id_contrato = id_contrato;
        this.id_tipo_de_problema = id_tipo_de_problema;
        this.id_tecnico = id_tecnico;
        this.fecha_creacion = new Date();
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin_estimada = fecha_fin_estimada;
        this.duracion_estimada = duracion_estimada;
        this.estado = estado;
        this.problemas = problemas;
    }

    // Getters y Setters

    public int getId_incidente() {
        return id_incidente;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public int getId_tipo_de_problema() {
        return id_tipo_de_problema;
    }

    public int getId_tecnico() {
        return id_tecnico;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public Date getFecha_fin_estimada() {
        return fecha_fin_estimada;
    }

    public String getEstado() {
        return estado;
    }

    public void setId_incidente(int id_incidente) {
        this.id_incidente = id_incidente;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public void setId_tipo_de_problema(int id_tipo_de_problema) {
        this.id_tipo_de_problema = id_tipo_de_problema;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin_estimada(Date fecha_fin_estimada) {
        this.fecha_fin_estimada = fecha_fin_estimada;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public Duration getDuracion_estimada() {
        return duracion_estimada;
    }

    public void setDuracion_estimada(Duration duracion_estimada) {
        this.duracion_estimada = duracion_estimada;
    }

    public List<Problema> getProblemas() {
        return problemas;
    }

    public void setProblemas(List<Problema> problemas) {
        this.problemas = problemas;
    }


    // Método para determinar la fecha de inicio basada en la disponibilidad del técnico
    public void determinarFechaInicio(int idTecnico, List<Incidente> incidentesAsignadosAlTecnico) {
        Incidente ultimoIncidente = obtenerUltimoIncidenteDelTecnico(incidentesAsignadosAlTecnico);

        if (ultimoIncidente == null) {
            // Si el técnico no tiene incidentes asignados, la fecha de inicio es la fecha de creación
            this.fecha_inicio = this.fecha_creacion;
        } else {
            // Si el técnico tiene incidentes asignados, calcular un día después de la fecha_fin_estimada del último incidente
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ultimoIncidente.getFecha_fin_estimada());
            calendar.add(Calendar.DATE, 1); // Añadir un día
            this.fecha_inicio = calendar.getTime();
        }
    }

    // Método para obtener el incidente con la fecha_fin_estimada más lejana entre los que no están resueltos
    private Incidente obtenerUltimoIncidenteDelTecnico(List<Incidente> incidentesAsignadosAlTecnico) {
        Incidente incidenteMasTardio = null;

        for (Incidente incidente : incidentesAsignadosAlTecnico) {
            if (incidente.getFecha_fin_estimada() != null && !incidente.getEstado().equalsIgnoreCase("resuelto")) {
                // Si es el primer incidente que estamos revisando o si la fecha_fin_estimada es más lejana que la del incidente más tardío registrado
                if (incidenteMasTardio == null || incidente.getFecha_fin_estimada().after(incidenteMasTardio.getFecha_fin_estimada())) {
                    incidenteMasTardio = incidente;
                }
            }
        }

        return incidenteMasTardio;
    }

    // Método para calcular la duración estimada del incidente
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

        if (fecha_inicio != null && fecha_inicio.after(fechaActual)) {
            return "pendiente";
        } else if (fecha_inicio != null && fecha_inicio.before(fechaActual) && (fecha_fin_estimada == null || fecha_fin_estimada.after(fechaActual))) {
            return "en proceso";
        } else if (fecha_fin_estimada != null && fecha_fin_estimada.before(fechaActual) && !todosProblemasResueltos) {
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

        return null; // Si no se cumplen las condiciones, devuelve null
    }
    public Date obtenerFechaFinEstimada() {
        if (fecha_inicio != null && duracion_estimada != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha_inicio);
            calendar.add(Calendar.HOUR, (int)duracion_estimada.toHours()); // Agregar la duración estimada en horas

            return calendar.getTime();
        }

        return null; // Si no se cumplen las condiciones, devuelve null
    }

    // Método de notificación al cliente al crear el incidente
    public void notificarClienteFechaPosibleSolucion(Incidente incidente) {

    }

    // Método para obtener la fecha posible de resolución (puedes personalizar cómo se calcula)
    private Date obtenerFechaPosibleResolucion(Incidente incidente) {
        // Aquí calcula la fecha posible de resolución
        // FALTA CODIGO
        return new Date();
    }

    public void notificarTecnico(Incidente incidente) {

    }

    public void notificarClienteEstadoResulto(Incidente incidente) {

    }

    // Método para obtener incidentes por fecha en una lista dada
    public static List<Incidente> obtenerIncidentesPorFechaEnLista(Date fecha, List<Incidente> listaIncidentes) {
        List<Incidente> incidentesPorFecha = new ArrayList<>();

        for (Incidente incidente : listaIncidentes) {
            if (incidente.getFecha_creacion() != null && incidente.getFecha_creacion().equals(fecha)) {
                incidentesPorFecha.add(incidente);
            }
        }

        return incidentesPorFecha;
    }


    // Método para obtener el técnico con más incidentes resueltos en los últimos N días
    public Tecnico obtenerTecnicoMasIncidentesResueltos(int dias, List<Incidente> listaIncidentes) {
        //FALTA CODIGO
        return null;

    }
    // Método que devuelve el técnico con más incidentes resueltos de una determinada especialidad en los últimos N días
    public Tecnico obtenerTecnicoMasIncidentesResueltosEspecialidad(int dias, int idEspecialidad, List<Incidente> listaIncidentes) {
        //FALTA CODIGO
        return null;
    }

    public Tecnico obtenerTecnicoMasRapido(List<Incidente> incidentes) {
        //FALTA CODIGO
        return null;
    }

}

