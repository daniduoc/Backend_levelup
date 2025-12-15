package cl.duoc.levelup.service;

import cl.duoc.levelup.model.Producto;
import cl.duoc.levelup.repository.CategoriaRepository;
import cl.duoc.levelup.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(int id) {
        return productoRepository.findById((long) id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public boolean existeNombre(String nombre) {
        return productoRepository.existsByNombre(nombre);
    }

    public Producto findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    public boolean existeCategoria(String categoria) {
        return categoriaRepository.existsByNombre(categoria);
    }
}