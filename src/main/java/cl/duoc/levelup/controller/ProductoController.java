package cl.duoc.levelup.controller;

import cl.duoc.levelup.dto.ProductoDTO;
import cl.duoc.levelup.model.Producto;
import cl.duoc.levelup.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/productos")
@Tag(name="Productos", description = "Operaciones sobre productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Obtener una lista de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<List<Producto>> getAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto", description = "Obtiene un producto a través del ID")
    @Parameter(description = "id del producto", required = true, name = "id")
    public ResponseEntity<Optional<Producto>> getById(@PathVariable int id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo producto", description = "Crea un producto a través de un objeto JSON")
    public ResponseEntity<?> save(@Valid @RequestBody ProductoDTO productoDTO) {
        try {
            // Verificar que la categoría existe
            if (!productoService.existeCategoria(productoDTO.getCategoria())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "La categoría '" + productoDTO.getCategoria() + "' no existe"));
            }

            // Verificar que el nombre es único
            if (productoService.existeNombre(productoDTO.getNombre())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Ya existe un producto con el nombre '" + productoDTO.getNombre() + "'"));
            }

            // Convertir DTO a entidad
            Producto producto = new Producto();
            producto.setNombre(productoDTO.getNombre());
            producto.setCategoria(productoDTO.getCategoria());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setImgUrl(productoDTO.getImgUrl());

            Producto newProducto = productoService.save(producto);
            return new ResponseEntity<>(newProducto, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Error de integridad: El nombre del producto ya existe"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al guardar el producto: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente por su ID")
    @Parameter(description = "id del producto", required = true, name = "id")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        try {
            Optional<Producto> productoExistente = productoService.findById(id.intValue());

            if (productoExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Producto no encontrado"));
            }

            // Verificar que la categoría existe
            if (!productoService.existeCategoria(productoDTO.getCategoria())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "La categoría '" + productoDTO.getCategoria() + "' no existe"));
            }

            // Verificar nombre único (excepto si es el mismo producto)
            Producto productoConMismoNombre = productoService.findByNombre(productoDTO.getNombre());
            if (productoConMismoNombre != null && productoConMismoNombre.getId() != id) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Ya existe otro producto con el nombre '" + productoDTO.getNombre() + "'"));
            }

            // Actualizar producto
            Producto producto = productoExistente.get();
            producto.setNombre(productoDTO.getNombre());
            producto.setCategoria(productoDTO.getCategoria());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setImgUrl(productoDTO.getImgUrl());

            Producto productoActualizado = productoService.save(producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Error de integridad: El nombre del producto ya existe"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar el producto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @Parameter(description = "id del producto", required = true, name = "id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Producto> producto = productoService.findById(id.intValue());

            if (producto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Producto no encontrado"));
            }

            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar el producto: " + e.getMessage()));
        }
    }

    // Manejo global de errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}