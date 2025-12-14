package cl.duoc.levelup.controller;

import cl.duoc.levelup.model.Categoria;
import cl.duoc.levelup.service.CategoriaService;
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
@RequestMapping("api/v1/categorias")
@Tag(name="Categorias", description = "Operaciones sobre categorías de productos")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Obtener una lista de todas las categorías disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<List<Categoria>> getAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría", description = "Obtiene una categoría a través del ID")
    @Parameter(description = "id de la categoría", required = true, name = "id")
    public ResponseEntity<Optional<Categoria>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea una nueva categoría", description = "Crea una categoría a través de un objeto JSON")
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria) {
        Categoria newCategoria = categoriaService.save(categoria);
        return new ResponseEntity<>(newCategoria, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}