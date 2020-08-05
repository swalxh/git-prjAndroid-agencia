package com.example.agencia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
    public static final String user="names";
    TextView txtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        txtUser =(TextView)findViewById(R.id.lblText);
        String user = getIntent().getStringExtra("names");
        txtUser.setText("¡Bienvenido "+ user +"!");
    }
}