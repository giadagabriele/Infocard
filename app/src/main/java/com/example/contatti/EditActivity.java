package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class EditActivity extends AppCompatActivity {
    // Write a message to the database
    private DatabaseReference databaseReference;
    Contatti c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        c=new Contatti("GiadaGabriele",349,"giadagabriele@outlook.com");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Contatti").child(c.getNickname()).setValue(c, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });

        final Button aggiungiEmail=findViewById(R.id.aggiungi_email);
        aggiungiEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dafare
            }
        });
        final Button aggiungiNumero=findViewById(R.id.aggiungi_numero);
        aggiungiNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dafare
            }
        });
        final Button indietro=findViewById(R.id.indietro_edit);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }
}