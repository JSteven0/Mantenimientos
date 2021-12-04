package com.example.mantenimientos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {
    private MyDBSQLiteHelper admin;
    private SQLiteDatabase db;
    private ContentValues cv;
    private Cursor fila;

    private EditText et1, et2, et3, et4, et5;
    private Spinner sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //Activar el soporte para la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        admin = new MyDBSQLiteHelper(this, Vars.db, null, Vars.vers);

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
        if(!dni.equals("") && !nombre.equals("") && !apellido.equals("") && !fecna.equals("") && !cargo.equals("")) {
            db = admin.getWritableDatabase();
            cv = new ContentValues();
            cv.put("dni", dni);
            cv.put("nombre", nombre);
            cv.put("apellido", apellido);
            cv.put("genero", genero);
            cv.put("fecna", fecna);
            cv.put("cargo", cargo);
            long reg = db.insert("perfil", null, cv);
            if(reg == -1) {
                Toast.makeText(this, "No se pudo agregar el registro", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro almacenado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et1.requestFocus();
                et2.setText("");
                et3.setText("");
                sp1.setSelection(0);
                et4.setText("");
                et5.setText("");
            }
            db.close();
        } else {
            Toast.makeText(this, "Por favor ingrese los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void BuscarPerfil(View view) {
        String dni = et1.getText().toString();
        if(!dni.equals("")){
            db = admin.getReadableDatabase();
            fila = db.rawQuery("SELECT * FROM perfil WHERE dni='"+dni+"'", null);
            if(fila.getCount() > 0){
                String datos = "";
                if (fila.moveToFirst()) {
                    et2.setText(fila.getString(2));
                    et3.setText(fila.getString(3));
                    Adapter adapter = sp1.getAdapter();
                    for(int i=0; i<adapter.getCount(); i++){
                        if(fila.getString(4).equals(adapter.getItem(i))){
                            sp1.setSelection(i);
                        }
                    }
                    et4.setText(fila.getString(5));
                    et5.setText(fila.getString(6));
                }
                db.close();
            } else {
                Toast.makeText(this, "El perfil no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingrese el número de identificación", Toast.LENGTH_SHORT).show();
        }
    }

    public void EditarPerfil(View view) {
        String dni = et1.getText().toString();
        String nombre = et2.getText().toString();
        String apellido = et3.getText().toString();
        String genero = sp1.getSelectedItem().toString();
        String fecna = et4.getText().toString();
        String cargo = et5.getText().toString();
        if(!dni.equals("") && !nombre.equals("") && !apellido.equals("") && !fecna.equals("") && !cargo.equals("")) {
            db = admin.getWritableDatabase();
            cv = new ContentValues();
            cv.put("nombre", nombre);
            cv.put("apellido", apellido);
            cv.put("genero", genero);
            cv.put("fecna", fecna);
            cv.put("cargo", cargo);
            long reg = db.update("perfil", cv, "dni='"+dni+"'", null);
            if(reg == 0) {
                Toast.makeText(this, "No se pudo editar el registro", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro editado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et1.requestFocus();
                et2.setText("");
                et3.setText("");
                sp1.setSelection(0);
                et4.setText("");
                et5.setText("");
            }
        } else {
            Toast.makeText(this, "Por favor ingrese los datos", Toast.LENGTH_SHORT).show();
        }

    }

    public void EliminarPerfil(View view) {
        String dni = et1.getText().toString();
        if(!dni.equals("")) {
            db = admin.getWritableDatabase();
            String [] args = new String[]{dni};
            int reg = db.delete("perfil", "dni=?", args);
            if(reg == 0){
                Toast.makeText(this, "No se pudo eliminar el registro", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et1.requestFocus();
                et2.setText("");
                et3.setText("");
                sp1.setSelection(0);
                et4.setText("");
                et5.setText("");
            }
            db.close();
        } else {
            Toast.makeText(this, "Por favor ingrese el número de identificación", Toast.LENGTH_SHORT).show();
        }
    }
}
