package com.example.mantenimientos;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class EstadoFormActivity extends AppCompatActivity {
    private ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_form);
        //Activar el soporte para la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv1 = findViewById(R.id.listView);
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
