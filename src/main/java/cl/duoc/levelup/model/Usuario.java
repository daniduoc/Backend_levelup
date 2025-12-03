package cl.duoc.levelup.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50)
    private String nombre;
    @Column(length = 50)
    private String apellido;
    @Column(unique = true, length = 100)
    private String correo;
    @Column(length = 300)
    private String password;
    private LocalDate fechaNacimiento;
    @Column(length = 30)
    private String tipoUsuario;
}
