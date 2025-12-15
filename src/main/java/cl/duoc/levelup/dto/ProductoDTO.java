package cl.duoc.levelup.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long id;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String categoria;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 200, message = "La descripción debe tener entre 10 y 200 caracteres")
    private String descripcion;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Pattern(regexp = "^/img/.*\\.(jpg|jpeg|png|webp|gif)$",
            message = "La URL debe empezar con /img/ y terminar en una extensión válida (.jpg, .jpeg, .png, .webp, .gif)")
    private String imgUrl;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    @Max(value = 999999999, message = "El precio no puede exceder 999,999,999")
    private Integer precio;
}