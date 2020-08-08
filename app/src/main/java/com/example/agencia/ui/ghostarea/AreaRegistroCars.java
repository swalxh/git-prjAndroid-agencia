package com.example.agencia.ui.ghostarea;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agencia.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AreaRegistroCars#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AreaRegistroCars extends Fragment {

    public AreaRegistroCars() { }

    private EditText modelo,marca,capacidad,color;
    private Image imagenvehi;
    private View vista;
    private ImageView imgRefVehicle;
    private Button btnSubir, btnSeleccionar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private  ProgressDialog cargando;
    private static final int GALLERY_INTENT = 1;
    long id = 0;

    Bitmap thumb_bitmap = null;

    public static AreaRegistroCars newInstance() {
        AreaRegistroCars fragment = new AreaRegistroCars();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_area_registro_cars, container, false);

        btnSubir = (Button) vista.findViewById(R.id.btnRegistrarVehicle);
        btnSeleccionar = (Button) vista.findViewById(R.id.btnSelectPic);
        modelo = vista.findViewById(R.id.txtModelo);
        marca = vista.findViewById(R.id.txtMarca);
        color = vista.findViewById(R.id.txtColor);
        capacidad = vista.findViewById(R.id.txtCapacidad);
        cargando = new ProgressDialog(getContext());

        btnSeleccionar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent ireResourceImage = new Intent(Intent.ACTION_PICK);
                ireResourceImage.setType("image/*");
            }
        });

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

        return vista;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            Uri uri = data.getData();
            StorageReference filePath = storageReference.child("VehiclesImages").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Se ha subido la imagen", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


    private void initFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void RegistrarVehicle(View view){
        String vmodelo = modelo.getText().toString().trim();
        String vmarca = marca.getText().toString().trim();
        String vcolor = color.getText().toString().trim();
        String vcapacidad = capacidad.getText().toString().trim();
        Image vImage = imagenvehi;
        if (TextUtils.isEmpty(vmodelo) || TextUtils.isEmpty(vmarca) || TextUtils.isEmpty(vcolor) || TextUtils.isEmpty(vcapacidad) || vImage == null) {//(precio.equals(""))
            Toast.makeText( getContext(),"No debes dejar espacios vacios", Toast.LENGTH_LONG).show();
            return;
        }else{}
    }

}