package cl.duoc.levelup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResponse {
    private Long id;
    private Long usuarioId;
    private Long productoId;
    private String nombreProducto;
    private String imgUrl;
    private Integer precio;
    private Integer cantidad;
    private Integer subtotal;
}