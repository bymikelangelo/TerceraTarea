package com.example.terceratarea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Editar extends AppCompatActivity {

    EditText editTitulo, editContenido;
    Spinner spinnerTitulosEdit;
    DaoChiste dao;
    BDChiste bd;
    String categoria;
    Button buttonBorrar, buttonAnyadir;
    List<Chiste> chistes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bd = BDChiste.obtenerBD(this);
        dao = bd.obtenerDao();

        categoria = getIntent().getStringExtra("categoria");

        editTitulo = findViewById(R.id.editTitulo);
        editContenido = findViewById(R.id.editContenido);
        spinnerTitulosEdit = findViewById(R.id.spinnerTitulosEdit);
        buttonAnyadir = findViewById(R.id.buttonAnyadir);
        buttonBorrar = findViewById(R.id.buttonBorrar);
        buttonBorrar.setEnabled(false);

        //consulta para recibir los objetos Chiste de la categoria seleccionada
        chistes = dao.obtenerChistes(categoria);

        //se crea una lista adicional con los títulos de los chistes mas una opción de añadir uno nuevo
        List<String> opciones = new ArrayList<>();
        opciones.add(getResources().getString(R.string.editar_opcion_nuevo));
        for (int i = 0; i < chistes.size(); i++) {
            opciones.add(chistes.get(i).getTitulo());
        }

        //se crea el adaptador del Spinner y el método Listener
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinnerTitulosEdit.setAdapter(adaptador);
        spinnerTitulosEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //dependiendo de la opcion seleccionada se cambia el funcionamiento del boton
                    case 0:  //cuando se desea añadir un nuevo chiste
                        buttonBorrar.setEnabled(false);
                        buttonAnyadir.setText(R.string.editar_boton_anyadir);
                        editContenido.setText("");
                        editTitulo.setText("");
                        buttonAnyadir.setOnClickListener(Editar.this::anyadir);
                        break;
                    default:  //cuando se desea editar un chiste ya existente
                        buttonBorrar.setEnabled(true);
                        buttonAnyadir.setText(R.string.editar_boton_editar);
                        editContenido.setText(chistes.get(position - 1).getContenido());
                        editTitulo.setText(chistes.get(position - 1).getTitulo());
                        buttonAnyadir.setOnClickListener(Editar.this::editar);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*
    metodo llamado cuando se desea añadir un nuevo chiste a la base de datos. Comprueba si los
    campos son vacios para evitar nulos. Si se añade, recarga la actividad.
     */
    public void anyadir(View vista) {
        String categoria = getIntent().getStringExtra("categoria");
        String titulo = editTitulo.getText().toString();
        String contenido = editContenido.getText().toString();

        Chiste nuevoChiste = new Chiste(categoria, titulo, contenido);
        if (!titulo.equals("") & !contenido.equals("")) {
            dao.insertar(nuevoChiste);
            recargarActividad();
        }
        else
            Toast.makeText(this, getString(R.string.editar_toast_campos_vacios), Toast.LENGTH_SHORT).show();
    }

    /*
    metodo llamado cuando se desea edita un chiste ya existente en la base de datos. Comprueba si
    los campos son vacios para evitar nulos. Si se edita el chiste, recarga la actividad.
     */
    public void editar(View vista) {
        int id = chistes.get(spinnerTitulosEdit.getSelectedItemPosition() - 1).getId();
        String titulo = editTitulo.getText().toString();
        String contenido = editContenido.getText().toString();
        Chiste chisteModificado = new Chiste(id, categoria, titulo, contenido);
        if (!titulo.equals("") & !contenido.equals("")) {
            dao.modificar(chisteModificado);
            recargarActividad();
        }
        else
            Toast.makeText(this, getString(R.string.editar_toast_campos_vacios), Toast.LENGTH_SHORT).show();
    }

    //borra el chiste seleccionado en el Spinner. Recarga la actividad.
    public void borrar(View vista) {
        dao.borrar(chistes.get(spinnerTitulosEdit.getSelectedItemPosition() - 1));
        recargarActividad();
    }

    //recarga la actividad para introducir los cambios efectuados al añadir, editar o borrar un chiste
    private void recargarActividad() {
        Intent intent = new Intent(this, Editar.class);
        intent.putExtra("categoria", categoria);
        startActivity(intent);
    }

}