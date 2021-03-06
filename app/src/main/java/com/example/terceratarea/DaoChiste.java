package com.example.terceratarea;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoChiste {
    @Insert
    void insertar(Chiste chiste);

    @Insert
    void insertarTodo(List<Chiste> chistes);

    @Delete
    void borrar(Chiste chiste);

    @Update
    void modificar(Chiste chiste);

    @Query("SELECT titulo FROM chistes WHERE categoria = :categoria")
    List<String> mostrarTitulos(String categoria);

    @Query("SELECT * FROM chistes WHERE categoria = :categoria")
    List<Chiste> obtenerChistes(String categoria);

    @Query("SELECT COUNT(*) FROM chistes WHERE categoria = :categoria")
    int numeroChistes(String categoria);
}
