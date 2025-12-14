package cl.duoc.levelup.controller;

import cl.duoc.levelup.dto.CarritoRequest;
import cl.duoc.levelup.dto.CarritoResponse;
import cl.duoc.levelup.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/carrito")
@Tag(name="Carrito", description = "Operaciones sobre el carrito de compras")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener carrito del usuario", description = "Obtiene todos los items del carrito de un usuario")
    @Parameter(description = "id del usuario", required = true, name = "usuarioId")
    public ResponseEntity<List<CarritoResponse>> obtenerCarrito(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoPorUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Agregar producto al carrito", description = "Agrega un producto al carrito del usuario")
    public ResponseEntity<CarritoResponse> agregarAlCarrito(@RequestBody CarritoRequest request) {
        CarritoResponse response = carritoService.agregarAlCarrito(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{carritoId}/cantidad/{cantidad}")
    @Operation(summary = "Actualizar cantidad", description = "Actualiza la cantidad de un item en el carrito")
    public ResponseEntity<Void> actualizarCantidad(
            @PathVariable Long carritoId,
            @PathVariable Integer cantidad) {
        carritoService.actualizarCantidad(carritoId, cantidad);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{carritoId}")
    @Operation(summary = "Eliminar item", description = "Elimina un item del carrito")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long carritoId) {
        carritoService.eliminarItem(carritoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}")
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los items del carrito de un usuario")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}