package com.notasapp.config;

import com.notasapp.model.Usuario;
import com.notasapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.admin-username}")
    private String adminUsername;

    @Value("${app.seed.admin-password}")
    private String adminPassword;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByUsername(adminUsername).isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setUsername(adminUsername);
            usuario.setPassword(passwordEncoder.encode(adminPassword));
            usuarioRepository.save(usuario);
            System.out.println(">> Usuario admin creado: " + adminUsername);
        }
    }
}
