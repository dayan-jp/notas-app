package com.notasapp.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class NotaRequest {
    @NotBlank
    private String titulo;

    private String contenido;

    private LocalDate fecha;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
