package com.anlahero.mistareas.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anlahero.mistareas.R;
import com.anlahero.mistareas.RecuperarContrasenaActivity;
import com.anlahero.mistareas.RegistroActivity;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private String email = "";
    private String password = "";
    private FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    private EditText tv1, tv2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        
        tv1 = findViewById(R.id.edusuario);
        tv2 = findViewById(R.id.edcontrasena);
        verificarUsuarioIngresado();
    }

    private void verificarUsuarioIngresado() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(LoginActivity.this,"Usuario registrado",Toast.LENGTH_SHORT).show();
        }
    }
    public void btnIngreso(View view){
        email=tv1.getText().toString();
        password=tv2.getText().toString();

        if (!email.isEmpty()&& !password.isEmpty()){
            loginUser();
        }else {
            Toast.makeText(LoginActivity.this,"Complete los campos",Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void registrarse(View view){
        Intent intent=new Intent(LoginActivity.this,RegistroActivity.class);
        startActivity(intent);
        finish();
    }
    public void recuperarContra(View view){
        Intent intent=new Intent(LoginActivity.this,RecuperarContrasenaActivity.class);
        startActivity(intent);
        finish();
    }
}