package cl.duoc.levelup;
import cl.duoc.levelup.model.Usuario;
import cl.duoc.levelup.repository.UsuarioRepository;
import cl.duoc.levelup.service.UsuarioService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //Si la lista no esta vacia, no agregamos nada.
        if (!usuarioRepository.findAll().isEmpty()) return;

        Faker faker = new Faker();
        Usuario admin = new Usuario();

        admin.setNombre(faker.name().firstName());
        admin.setApellido(faker.name().lastName());
        admin.setCorreo("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setFechaNacimiento(LocalDate.now().plusYears(faker.number().numberBetween(-40,-20)));
        admin.setTipoUsuario("Admin");

        usuarioRepository.save(admin);
    }
}