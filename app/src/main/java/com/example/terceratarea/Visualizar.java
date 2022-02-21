package com.example.terceratarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    MediaPlayer reproductor;
    Button buttonSiguiente, buttonAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bd = BDChiste.obtenerBD(this);
        dao = bd.obtenerDao();

        spinnerTitulos = findViewById(R.id.spinnerTitulosVis);
        textContenido = findViewById(R.id.textContenido);
        ConstraintLayout layout = findViewById(R.id.layoutVisualizar);
        buttonAnterior = findViewById(R.id.buttonAnterior);
        buttonSiguiente = findViewById(R.id.buttonSiguiente);

        categoria = getIntent().getStringExtra("categoria");
        switch (categoria) {
            case "manyos":
                layout.setBackground(getDrawable(R.drawable.manyico_mod));
                reproductor = MediaPlayer.create(this, R.raw.modorro);
                cambiarColor(android.R.color.holo_red_dark);
                break;
            case "sexo":
                layout.setBackground(getDrawable(R.drawable.botella_mod));
                reproductor = MediaPlayer.create(this, R.raw.ohhh_my_god);
                cambiarColor(android.R.color.holo_purple);
                break;
            case "informatica":
                layout.setBackground(getDrawable(R.drawable.informatico_mod));
                reproductor = MediaPlayer.create(this, R.raw.error_windows);
                cambiarColor(android.R.color.holo_blue_light);
                break;
            default:
                break;
        }

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
                reproductor.start();
                if (pos <= 0) {
                    disable(buttonAnterior);
                    enable(buttonSiguiente);
                }
                else if(pos >= titulos.size() - 1) {
                    disable(buttonSiguiente);
                    enable(buttonAnterior);
                }
                else {
                    enable(buttonAnterior);
                    enable(buttonSiguiente);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reproductor.release();
        reproductor = null;
    }

    //mueve en +1 la posición del Spinner de selección
    public void siguiente(View vista) {
        spinnerTitulos.setSelection(spinnerTitulos.getSelectedItemPosition() + 1, true);
    }

    //mueve en -1 la posición del Spinner de selección
    public void anterior(View vista) {
        spinnerTitulos.setSelection(spinnerTitulos.getSelectedItemPosition() - 1, true);
    }

    //cambia el color de los elementos dependiendo de la categoría seleccionada
    public void cambiarColor(int color) {
        spinnerTitulos.setBackgroundColor(getColor(color));
        spinnerTitulos.setPopupBackgroundResource(color);
        buttonSiguiente.setBackgroundColor(getColor(color));
        buttonAnterior.setBackgroundColor(getColor(color));
    }

    //modifica apariencia y habilita el botón pasado por parámetro
    public void enable(Button boton) {
        boton.setEnabled(true);
        boton.setAlpha(1f);
    }

    //modifica apariencia y desabilita el botón pasado por parámetro
    public void disable(Button boton) {
        boton.setEnabled(false);
        boton.setAlpha(0.7f);
    }
}