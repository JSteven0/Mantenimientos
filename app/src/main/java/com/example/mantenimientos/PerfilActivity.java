package com.example.mantenimientos;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {
    private EditText et1, et2, et3, et4, et5;
    private Spinner sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //Activar el soporte para la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et1 = findViewById(R.id.ET_dni);
        et2 = findViewById(R.id.ET_nombrep);
        et3 = findViewById(R.id.ET_apellidop);
        et4 = findViewById(R.id.ET_fecna);
        et5 = findViewById(R.id.ET_cargo);
        sp1 = findViewById(R.id.SP_generop);
    }

    //Destuir la aplicación
    public void onBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void guardarPerfil(View view) {
        String dni = et1.getText().toString();
        String nombre = et2.getText().toString();
        String apellido = et3.getText().toString();
        String genero = sp1.getSelectedItem().toString();
        String fecna = et4.getText().toString();
        String cargo = et5.getText().toString();
    }
}
