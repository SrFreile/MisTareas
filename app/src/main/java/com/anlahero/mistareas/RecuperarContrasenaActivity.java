package com.anlahero.mistareas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anlahero.mistareas.view.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContrasenaActivity extends AppCompatActivity {

    EditText edcorreoreset;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        edcorreoreset=findViewById(R.id.edcorreoreset);
        mAuth=FirebaseAuth.getInstance();
    }
    public void recuperarcontra(View view){
        mAuth.setLanguageCode("es");

        String email=edcorreoreset.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            mAuth.setLanguageCode("es");
            Toast.makeText(getApplicationContext(),"ingrese correo",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mAuth.setLanguageCode("es");
                    startActivity(new Intent(RecuperarContrasenaActivity.this,LoginActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(),"Te enviamos un enlace a tu correo",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"correo no registrado",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}