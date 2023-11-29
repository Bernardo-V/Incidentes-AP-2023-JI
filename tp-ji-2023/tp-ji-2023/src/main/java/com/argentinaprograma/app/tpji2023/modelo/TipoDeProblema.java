package com.argentinaprograma.app.tpji2023.modelo;

import jakarta.persistence.*;
import com.argentinaprograma.app.tpji2023.modelo.TipoDeProblema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
//@ToString
@Table(name="tipodeproblema")
public class TipoDeProblema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id_tipo_de_problema;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "id_detalle_servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleServicio> detalleservicio = new ArrayList<>();


}
