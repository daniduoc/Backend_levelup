package cl.duoc.levelup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoRequest {
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
}