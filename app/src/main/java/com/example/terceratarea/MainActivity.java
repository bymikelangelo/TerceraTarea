package com.example.terceratarea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner categorias;
    String categoria;
    Button visualizar, editar;
    DaoChiste dao;
    BDChiste bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = BDChiste.obtenerBD(this);
        dao = bd.obtenerDao();

        categoria = "";

        visualizar = findViewById(R.id.buttonVisualizar);
        visualizar.setEnabled(false);
        editar = findViewById(R.id.buttonEditar);
        editar.setEnabled(false);

        categorias = findViewById(R.id.spinnerCategorias);
        ArrayAdapter adaptador = ArrayAdapter.createFromResource(this, R.array.categorias,
                android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adaptador);
        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                switch (pos) {
                    case 1:
                        categoria = "sexo";
                        visualizar.setEnabled(true);
                        editar.setEnabled(true);
                        break;
                    case 2:
                        categoria = "informatica";
                        visualizar.setEnabled(true);
                        editar.setEnabled(true);
                        break;
                    case 3:
                        categoria = "manyos";
                        visualizar.setEnabled(true);
                        editar.setEnabled(true);
                        break;
                    default:
                        visualizar.setEnabled(false);
                        editar.setEnabled(false);
                        editar = findViewById(R.id.buttonEditar);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void aActivity(View vista) {
        Intent intent;
        switch (vista.getId()) {
            case R.id.buttonVisualizar:
                intent = new Intent(this, Visualizar.class);
                if (dao.numeroChistes(categoria) > 0) {
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, R.string.main_toast_sin_resultados,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonEditar:
                intent = new Intent(this, Editar.class);
                intent.putExtra("categoria", categoria);
                startActivity(intent);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }
    }
}