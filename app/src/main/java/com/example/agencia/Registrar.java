package com.example.agencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agencia.Model.Persona;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registrar extends AppCompatActivity {

    private EditText nom,tel,email,pass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    long id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        firebaseAuth = FirebaseAuth.getInstance();
        nom=findViewById(R.id.txtNombre);
        tel=findViewById(R.id.txtTelefono);
        email=findViewById(R.id.txtEmail);
        pass=findViewById(R.id.txtContrasenia);
        inicializarFirebase();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    id=(snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
    public void iniciarsesion(View view){
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }


    public void RegistrarM(View view){
        String nombre = nom.getText().toString().trim();
        String telefono=tel.getText().toString().trim();
        String correo=email.getText().toString().trim();
        String password=pass.getText().toString().trim();
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(telefono) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(password)) {//(precio.equals(""))
            Toast.makeText(this, "No debes dejar espacios vacios", Toast.LENGTH_LONG).show();
            return;
        }else{

            Persona p = new Persona();
            p.setNombre(nombre);
            p.setTelefono(telefono);
            p.setCorreo(correo);
            p.setPassword(password);
            databaseReference.child("Persona").child(String.valueOf(id+1)).setValue(p);
            firebaseAuth.createUserWithEmailAndPassword(correo, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                Toast.makeText(Registrar.this, "Registro con exito Puede Loggearse ", Toast.LENGTH_LONG).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(Registrar.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                                } else {
                                   // Toast.makeText(Registrar.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                                    Toast.makeText(Registrar.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
            Limpiar();
        }


    }
    public void Limpiar(){
        nom.setText("");
        tel.setText("");
        email.setText("");
        pass.setText("");
    }
}