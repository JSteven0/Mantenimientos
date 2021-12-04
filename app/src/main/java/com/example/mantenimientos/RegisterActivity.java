package com.example.mantenimientos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtNombre, txtCorreo, txtTelefono, txtContraseña;
    private Button btnRegistrarse;

    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNombre = findViewById(R.id.editTextNombreReg);
        txtCorreo = findViewById(R.id.editTextCorreoReg);
        txtTelefono = findViewById(R.id.editTextTelReg);
        txtContraseña = findViewById(R.id.editTextContraseñaReg);
        btnRegistrarse = findViewById(R.id.button_RegistrarUser);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegistrarse.setOnClickListener(view -> {
            crearUsuario();
        });
    }

    public void crearUsuario(){
        String name = txtNombre.getText().toString();
        String mail = txtCorreo.getText().toString();
        String phone = txtTelefono.getText().toString();
        String password = txtContraseña.getText().toString();

        if (TextUtils.isEmpty(name)) {
            txtNombre.setError("Ingrese un Nombre");
            txtNombre.requestFocus();
        }else if (TextUtils.isEmpty(mail)){
            txtCorreo.setError("Ingrese un Correo");
            txtCorreo.requestFocus();
        }else if (TextUtils.isEmpty(phone)){
            txtTelefono.setError("Ingrese un Teléfono");
            txtTelefono.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            txtContraseña.setError("Ingrese una Contraseña");
            txtContraseña.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String,Object> user= new HashMap<>();
                        user.put("Nombre", name);
                        user.put("Correo", mail);
                        user.put("Teléfono", phone);
                        user.put("Contraseña", password);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG","onSuccess: Datos Registrados"+userID);
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, Login.class));
                    }else {
                        Toast.makeText(RegisterActivity.this, "Usuario no registrado"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}