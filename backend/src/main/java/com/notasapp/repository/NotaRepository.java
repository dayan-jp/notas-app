package com.notasapp.repository;

import com.notasapp.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
    boolean existsByIdAndUsuarioId(Long id, Long usuarioId);
}
