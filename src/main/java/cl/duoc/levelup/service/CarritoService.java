package cl.duoc.levelup.service;

import cl.duoc.levelup.dto.CarritoRequest;
import cl.duoc.levelup.dto.CarritoResponse;
import cl.duoc.levelup.model.Carrito;
import cl.duoc.levelup.model.Producto;
import cl.duoc.levelup.model.Usuario;
import cl.duoc.levelup.repository.CarritoRepository;
import cl.duoc.levelup.repository.ProductoRepository;
import cl.duoc.levelup.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<CarritoResponse> obtenerCarritoPorUsuario(Long usuarioId) {
        List<Carrito> items = carritoRepository.findByUsuarioId(usuarioId);

        return items.stream().map(item -> {
            CarritoResponse response = new CarritoResponse();
            response.setId(item.getId());
            response.setUsuarioId(item.getUsuario().getId());
            response.setProductoId(item.getProducto().getId());
            response.setNombreProducto(item.getProducto().getNombre());
            response.setImgUrl(item.getProducto().getImgUrl());
            response.setPrecio(item.getProducto().getPrecio());
            response.setCantidad(item.getCantidad());
            response.setSubtotal(item.getProducto().getPrecio() * item.getCantidad());
            return response;
        }).collect(Collectors.toList());
    }

    @Transactional
    public CarritoResponse agregarAlCarrito(CarritoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si el producto ya está en el carrito
        Optional<Carrito> carritoExistente = carritoRepository
                .findByUsuarioIdAndProductoId(request.getUsuarioId(), request.getProductoId());

        Carrito carrito;
        if (carritoExistente.isPresent()) {
            // Si existe, actualizar cantidad
            carrito = carritoExistente.get();
            carrito.setCantidad(carrito.getCantidad() + request.getCantidad());
        } else {
            // Si no existe, crear nuevo item
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setProducto(producto);
            carrito.setCantidad(request.getCantidad());
        }

        carrito = carritoRepository.save(carrito);

        // Convertir a response
        CarritoResponse response = new CarritoResponse();
        response.setId(carrito.getId());
        response.setUsuarioId(carrito.getUsuario().getId());
        response.setProductoId(carrito.getProducto().getId());
        response.setNombreProducto(carrito.getProducto().getNombre());
        response.setImgUrl(carrito.getProducto().getImgUrl());
        response.setPrecio(carrito.getProducto().getPrecio());
        response.setCantidad(carrito.getCantidad());
        response.setSubtotal(carrito.getProducto().getPrecio() * carrito.getCantidad());

        return response;
    }

    @Transactional
    public void actualizarCantidad(Long carritoId, Integer cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (cantidad <= 0) {
            carritoRepository.delete(carrito);
        } else {
            carrito.setCantidad(cantidad);
            carritoRepository.save(carrito);
        }
    }

    @Transactional
    public void eliminarItem(Long carritoId) {
        carritoRepository.deleteById(carritoId);
    }

    @Transactional
    public void vaciarCarrito(Long usuarioId) {
        carritoRepository.deleteByUsuarioId(usuarioId);
    }
}