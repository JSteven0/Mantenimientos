package com.example.mantenimientos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class FormulariosActivity extends AppCompatActivity {
    private ImageButton btnCamara;
    private ImageView imgView;

    private EditText et1, et2, et3, et4, et5, et6, et7;
    private Spinner sp1, sp2, sp3, sp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios);
        //Activar el soporte para la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et1 = findViewById(R.id.ET_nombre);
        et2 = findViewById(R.id.ET_apellidos);
        et3 = findViewById(R.id.ET_codigo);
        et4 = findViewById(R.id.ET_estado);
        et5 = findViewById(R.id.ET_marca);
        et6 = findViewById(R.id.ET_serial);
        et7 = findViewById(R.id.ET_especificacion);
        sp1 = findViewById(R.id.SP_equipo);
        sp2 = findViewById(R.id.SP_hardware);
        sp3 = findViewById(R.id.SP_software);
        sp4 = findViewById(R.id.SP_solucion);

        btnCamara = findViewById(R.id.BT_camara);
        imgView = findViewById(R.id.IV_equipo);
        imgView.setImageDrawable(null);
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

    public void abrirCamara(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
    }

    public void RellenarFormulario(View view) {
        String nombre = et1.getText().toString();
        String apellidos = et2.getText().toString();
        String equipo = sp1.getSelectedItem().toString();
        String marca = et3.getText().toString();
        String serial = et4.getText().toString();
        String ProbHardware = sp2.getSelectedItem().toString();
        String ProbSoftware = sp3.getSelectedItem().toString();
        String especific = et5.getText().toString();
        String solucion = sp4.getSelectedItem().toString();


    }
}
