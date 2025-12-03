package cl.duoc.levelup.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50)
    private String categoria;

    @Column(length = 50)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(length = 50)
    private String imgUrl;

    @Column(length = 50)
    private Integer precio;
}
