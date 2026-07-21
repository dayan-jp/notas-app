package com.notasapp.controller;

import com.notasapp.dto.NotaRequest;
import com.notasapp.dto.NotaResponse;
import com.notasapp.model.Nota;
import com.notasapp.model.Usuario;
import com.notasapp.repository.NotaRepository;
import com.notasapp.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaController(NotaRepository notaRepository, UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario usuarioActual(Authentication auth) {
        String username = auth.getName();
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @GetMapping
    public List<NotaResponse> listar(Authentication auth) {
        Usuario usuario = usuarioActual(auth);
        return notaRepository.findByUsuarioIdOrderByFechaDesc(usuario.getId())
                .stream().map(NotaResponse::new).toList();
    }

    @PostMapping
    public ResponseEntity<NotaResponse> crear(@Valid @RequestBody NotaRequest request, Authentication auth) {
        Usuario usuario = usuarioActual(auth);

        Nota nota = new Nota();
        nota.setUsuario(usuario);
        nota.setTitulo(request.getTitulo());
        nota.setContenido(request.getContenido());
        nota.setFecha(request.getFecha() != null ? request.getFecha() : LocalDate.now());

        notaRepository.save(nota);
        return ResponseEntity.ok(new NotaResponse(nota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody NotaRequest request, Authentication auth) {
        Usuario usuario = usuarioActual(auth);

        if (!notaRepository.existsByIdAndUsuarioId(id, usuario.getId())) {
            return ResponseEntity.status(404).body("Nota no encontrada");
        }

        Nota nota = notaRepository.findById(id).orElseThrow();
        nota.setTitulo(request.getTitulo());
        nota.setContenido(request.getContenido());
        if (request.getFecha() != null) {
            nota.setFecha(request.getFecha());
        }

        notaRepository.save(nota);
        return ResponseEntity.ok(new NotaResponse(nota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, Authentication auth) {
        Usuario usuario = usuarioActual(auth);

        if (!notaRepository.existsByIdAndUsuarioId(id, usuario.getId())) {
            return ResponseEntity.status(404).body("Nota no encontrada");
        }

        notaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
