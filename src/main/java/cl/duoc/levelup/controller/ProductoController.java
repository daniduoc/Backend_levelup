package cl.duoc.levelup.controller;

import cl.duoc.levelup.model.Producto;
import cl.duoc.levelup.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<Producto>> getAll() { return ResponseEntity.ok(productoService.findAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto", description = "Obtiene un producto a traves del ID")
    @Parameter(description = "id del producto", required = true, name = "id")
    public ResponseEntity<Optional<Producto>> getById(@PathVariable int id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo producto", description = "Crea un producto a traves de un objeto JSON")
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return new ResponseEntity<>(newProducto, HttpStatus.CREATED);
    }
}
