package cl.duoc.levelup;

import cl.duoc.levelup.model.Categoria;
import cl.duoc.levelup.model.Producto;
import cl.duoc.levelup.model.Usuario;
import cl.duoc.levelup.repository.CategoriaRepository;
import cl.duoc.levelup.repository.ProductoRepository;
import cl.duoc.levelup.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Cargar categorías PRIMERO
        if (categoriaRepository.findAll().isEmpty()) {
            List<Categoria> categorias = List.of(
                    new Categoria(null, "juegos-mesa"),
                    new Categoria(null, "accesorios"),
                    new Categoria(null, "consolas"),
                    new Categoria(null, "pc-gamer"),
                    new Categoria(null, "silla-gamer"),
                    new Categoria(null, "mouse"),
                    new Categoria(null, "mousepad"),
                    new Categoria(null, "poleras"),
                    new Categoria(null, "polerones")
            );

            categoriaRepository.saveAll(categorias);
            System.out.println("✅ Categorías cargadas: " + categorias.size());
        }

        if (usuarioRepository.findAll().isEmpty()) {
            Faker faker = new Faker();
            Usuario admin = new Usuario();

            admin.setNombre(faker.name().firstName());
            admin.setApellido(faker.name().lastName());
            admin.setCorreo("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setFechaNacimiento(LocalDate.now().plusYears(faker.number().numberBetween(-40,-20)));
            admin.setTipoUsuario("Admin");

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario admin creado");
        }

        if (productoRepository.findAll().isEmpty()) {
            List<Producto> productos = List.of(
                    new Producto(0, "juegos-mesa", "Catan",
                            "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.",
                            "/img/catan.webp", 29990),

                    new Producto(0, "juegos-mesa", "Carcassonne",
                            "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.",
                            "/img/carcassonne.jpg", 24990),

                    new Producto(0, "accesorios", "Controlador Xbox Series X",
                            "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.",
                            "/img/control-xbox.jpg", 59990),

                    new Producto(0, "accesorios", "Auriculares HyperX Cloud II",
                            "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.",
                            "/img/cloud2.jpg", 79990),

                    new Producto(0, "consolas", "Playstation 5",
                            "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.",
                            "/img/ps5.webp", 549990),

                    new Producto(0, "pc-gamer", "PC Gamer ASUS ROG Strix",
                            "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.",
                            "/img/pc-gamer.jpg", 1299990),

                    new Producto(0, "silla-gamer", "Silla Gamer Secretlab Titan",
                            "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.",
                            "/img/silla.webp", 349990),

                    new Producto(0, "mouse", "Mouse Gamer Logitech G502 HERO",
                            "Con sensor de alta precisión y botones personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.",
                            "/img/mouse.webp", 49990),

                    new Producto(0, "mousepad", "Mousepad Razer Goliathus Exteded Chroma",
                            "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.",
                            "/img/mousepad.webp", 29990),

                    new Producto(0, "poleras", "Polera Negra Personalizada 'Level-Up'",
                            "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.",
                            "/img/polera.png", 14990),

                    new Producto(0, "poleras", "Polera Roja Personalizada 'Level-Up'",
                            "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.",
                            "/img/poleraRoja.png", 14990),

                    new Producto(0, "polerones", "Polerón Negro Personalizado 'Level-Up'",
                            "Un polerón cómodo y versátil, con un diseño moderno que puedes personalizar con tu gamer tag o gráfico favorito.",
                            "/img/poleron.png", 25990),

                    new Producto(0, "polerones", "Polerón Rojo Personalizado 'Level-Up'",
                            "Un polerón cómodo y versátil, con un diseño moderno que puedes personalizar con tu gamer tag o gráfico favorito.",
                            "/img/poleronRojo.png", 25990)
            );

            productoRepository.saveAll(productos);
            System.out.println("✅ Productos cargados: " + productos.size());
        }
    }
}