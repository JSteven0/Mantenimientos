package com.example.mantenimientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MenuMainActivity extends AppCompatActivity {

    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MenuMainActivity.this,Login.class));
        }
    }

    public void goToFormularios(View view) {
        Intent newIntent = new Intent(this, FormulariosActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);

    }

    public void goToEstadosForm(View view) {
        Intent newIntent = new Intent(this, EstadoFormActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);

    }

    public void goToPerfil(View view) {
        Intent newIntent = new Intent(this, PerfilActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);

    }

    public void salir(View view){
        mAuth.signOut();
        finish();
    }
}