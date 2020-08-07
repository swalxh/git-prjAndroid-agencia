package com.example.agencia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agencia.ui.userprofile.UserProfileData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.txtEmail);
        pass=findViewById(R.id.txtContrasenia);
    }
    public void registrar(View view){
        Intent i = new Intent(this, Registrar.class );
        startActivity(i);
    }
    public void Acceder(View view){
        final String correo = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (TextUtils.isEmpty(correo)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            Intent ifrmProf = new Intent(getApplication(), MenuAfterAcess.class);
                            startActivity(ifrmProf);

                            /*
                            int pos = correo.indexOf("@");
                            String user = correo.substring(0, pos);
                            Toast.makeText(MainActivity.this, "Bienvenido: " + email.getText(), Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), Perfil.class);
                            intencion.putExtra(Perfil.user, user);
                            startActivity(intencion);*/


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(MainActivity.this, "Ese usuario y/o contraseña incorrecta ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo encontrar ese usuario pruebe datos ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}