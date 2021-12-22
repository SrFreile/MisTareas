package com.anlahero.mistareas;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anlahero.mistareas.view.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RegistroActivity extends AppCompatActivity {

    EditText ednombre, edcorreo, edpass;
    private String name="";
    private String correo="";
    private String password="";

    FirebaseAuth mAuth;
    com.google.firebase.database.DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ednombre=findViewById(R.id.ednombre);
        edcorreo=findViewById(R.id.edcorreo);
        edpass=findViewById(R.id.edpassword);

        mAuth=FirebaseAuth.getInstance();
        mdatabase= FirebaseDatabase.getInstance().getReference();
    }
    public void ingresarbtn(View view){
        name=ednombre.getText().toString();
        correo=edcorreo.getText().toString();
        password=edpass.getText().toString();

        if(!name.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
            registroUsuario();
        }else{
            Toast.makeText(RegistroActivity.this,"Debe completar los campos",Toast.LENGTH_SHORT).show();
        }
        //public void serra(View view){
        //   FirebaseAuth.getInstance().signOut();
        // finish();//
    }

    private void registroUsuario() {
        mAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String,Object> map= new HashMap<>();

                    map.put("name", name);
                    map.put("correo", correo);
                    map.put("password", password);

                    String id= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                    mdatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if (task1.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                                finish();
                                Toast.makeText(RegistroActivity.this,"Registro correctamente",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegistroActivity.this,"No se pudo registrar",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegistroActivity.this,"Registro correctamente",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}