package cl.duoc.levelup.service;

import cl.duoc.levelup.model.Producto;
import cl.duoc.levelup.model.Usuario;
import cl.duoc.levelup.repository.ProductoRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() { return productoRepository.findAll(); }
    public Optional<Producto> findById(long id){ return productoRepository.findById(id); }
    public Producto save(Producto producto){ return productoRepository.save(producto); }
    public void deleteById(long id){
        productoRepository.deleteById(id);
    }
}
