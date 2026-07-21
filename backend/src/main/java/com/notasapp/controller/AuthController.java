package com.notasapp.controller;

import com.notasapp.dto.LoginRequest;
import com.notasapp.dto.LoginResponse;
import com.notasapp.model.Usuario;
import com.notasapp.repository.UsuarioRepository;
import com.notasapp.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElse(null);

        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }

        String token = jwtService.generateToken(usuario.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
