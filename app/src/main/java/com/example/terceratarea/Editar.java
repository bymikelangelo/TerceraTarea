package com.example.terceratarea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Editar extends AppCompatActivity {

    EditText editTitulo, editContenido;
    Spinner spinnerTitulosEdit;
    DaoChiste dao;
    BDChiste bd;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bd = BDChiste.obtenerBD(this);
        dao = bd.obtenerDao();

        categoria = getIntent().getStringExtra(categoria);

        editTitulo = findViewById(R.id.editTitulo);
        editContenido = findViewById(R.id.editContenido);
        spinnerTitulosEdit = findViewById(R.id.spinnerTitulosEdit);

        List<Chiste> chistes = dao.obtenerChistes(categoria);
        List<String> opciones = new ArrayList<>();
        opciones.add(getResources().getString(R.string.editar_opcion_nuevo));
        for (int i = 0; i < chistes.size(); i++) {
            opciones.add(chistes.get(i).getTitulo());
        }

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinnerTitulosEdit.setAdapter(adaptador);
    }

    public void anyadir(View vista) {
        String categoria = getIntent().getStringExtra("categoria");
        String titulo = editTitulo.getText().toString();
        String contenido = editContenido.getText().toString();

        Chiste chiste = new Chiste(categoria, titulo, contenido);

        dao.insertarTodo(chiste);
    }

}