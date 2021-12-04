package com.example.mantenimientos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EstadoFormActivity extends AppCompatActivity {
    private ListView lv1;

    private MyDBSQLiteHelper admin;
    private SQLiteDatabase db;
    private Cursor fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_form);
        //Activar el soporte para la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        admin = new MyDBSQLiteHelper(this, Vars.db, null, Vars.vers);
        setTitle("Estado Formularios");

        lv1 = findViewById(R.id.listView);
        ArrayList<String> listado = new ArrayList<String>();
        db = admin.getReadableDatabase();
        fila = db.rawQuery("SELECT * FROM formulario", null);
        while (fila.moveToNext()) {
            listado.add(fila.getString(0) + "-" + fila.getString(3) + "-" + fila.getString(5) + "-" + fila.getString(6) + "-" + fila.getString(4) + "\n");
        }
        db.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listado);
        lv1.setAdapter(adapter);
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
}
