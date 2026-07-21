package com.notasapp.dto;

import com.notasapp.model.Nota;
import java.time.LocalDate;

public class NotaResponse {
    private Long id;
    private String titulo;
    private String contenido;
    private LocalDate fecha;

    public NotaResponse(Nota nota) {
        this.id = nota.getId();
        this.titulo = nota.getTitulo();
        this.contenido = nota.getContenido();
        this.fecha = nota.getFecha();
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public LocalDate getFecha() { return fecha; }
}
