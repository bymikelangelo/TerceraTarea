package com.example.terceratarea;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "chistes")
public class Chiste {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String categoria;
    @NonNull
    private String titulo;
    @NonNull
    private String contenido;

    public Chiste(@NonNull String categoria, @NonNull String titulo, @NonNull String contenido) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    @Ignore
    public Chiste(int id, @NonNull String categoria, @NonNull String titulo, @NonNull String contenido) {
        this.id = id;
        this.categoria = categoria;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    @NonNull
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NonNull String titulo) {
        this.titulo = titulo;
    }

    @NonNull
    public String getContenido() {
        return contenido;
    }

    public void setContenido(@NonNull String contenido) {
        this.contenido = contenido;
    }
}
