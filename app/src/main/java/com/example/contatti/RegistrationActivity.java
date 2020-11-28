package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contatti.ui.login.Login;
import com.example.contatti.ui.login.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final Button indietro=findViewById(R.id.indietro_registrazione);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();

            }
        });
        mAuth = FirebaseAuth.getInstance();
        final Button salva=findViewById(R.id.salva_registrazione);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText usernameEditText = findViewById(R.id.registra_email);
                final EditText passwordEditText = findViewById(R.id.registra_password);
                String Email=usernameEditText.getText().toString().trim();
                String Pass=passwordEditText.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "REGISTRAZIONE EFFETTUATA", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, "ERRORE", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                        }
                        finish();
                    }
                });
            }
        });
    }
}