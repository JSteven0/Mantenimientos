package com.example.mantenimientos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText txtMail, txtPassword;
    private Button buttonIngresar;
    private TextView txtRegistrarse;

    
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        txtMail = findViewById(R.id.editText_usuario);
        txtPassword = findViewById(R.id.editTextPassword);
        txtRegistrarse = findViewById(R.id.textRegistrarse);
        buttonIngresar = findViewById(R.id.buttonIngresar);
        
        mAuth = FirebaseAuth.getInstance();

        txtRegistrarse.setOnClickListener(view -> {
            Intent newIntent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(newIntent);
        });
    }

    public void iniciarSesion(View view){
        String mail = txtMail.getText().toString();
        String password = txtPassword.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            txtMail.setError("Ingrese un correo");
            txtMail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            txtPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        Intent newIntent = new Intent(getApplicationContext(), MenuMainActivity.class);
                        startActivity(newIntent);
                        finish();
                    }else {
                        Log.w("TAG","Error:", task.getException());
                    }
                }
            });
        }



    }
}