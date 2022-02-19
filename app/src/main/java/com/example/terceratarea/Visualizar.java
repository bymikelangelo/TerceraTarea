package com.example.terceratarea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Visualizar extends AppCompatActivity {

    Spinner spinnerTitulos;
    TextView textContenido;
    BDChiste bd;
    DaoChiste dao;
    String categoria = "";
    List<Chiste> chistes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bd = BDChiste.obtenerBD(this);
        dao = bd.obtenerDao();

        spinnerTitulos = findViewById(R.id.spinnerTitulosVis);
        textContenido = findViewById(R.id.textContenido);
        categoria = getIntent().getStringExtra("categoria");

        /*
        consulta a la base de datos para recibir los chistes de la categoria seleccionada.
        Se guardan en una lista los Chistes completos
         */
        chistes = dao.obtenerChistes(categoria);

        /*
        se crea una nueva lista con unicamente los campos título de los chistes para el Spinner
         */
        List<String> titulos = new ArrayList<>();
        for (int i = 0; i < chistes.size(); i++) {
            titulos.add(chistes.get(i).getTitulo());
        }

        //Se asocia la lista de titulos al adaptador y luego al Spinner
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, titulos);
        spinnerTitulos.setAdapter(adaptador);
        //metodo Listener del Spinner
        spinnerTitulos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                textContenido.setText(chistes.get(pos).getContenido());
                textContenido.setMovementMethod(new ScrollingMovementMethod());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}