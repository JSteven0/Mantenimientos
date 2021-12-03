package com.example.mantenimientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void iniciarSesion(View view){
        Intent newIntent = new Intent(getApplicationContext(), MenuMainActivity.class);
        startActivity(newIntent);
        finish();
    }
}