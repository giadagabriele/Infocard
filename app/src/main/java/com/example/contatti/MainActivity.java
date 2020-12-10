 package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contatti.ui.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

 public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        else {
            setContentView(R.layout.activity_main);
            databaseReference= FirebaseDatabase.getInstance().getReference();
            final TextView nicknameEditText = findViewById(R.id.contatto_nickname);
            final TextView emailEditText = findViewById(R.id.contatto_email);
            final TextView numberEditText = findViewById(R.id.contatto_numeroDiTelefono);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String key=auth.getCurrentUser().getEmail();
                        nicknameEditText.setText(snapshot.child(key).child("nickname").getValue().toString());
                        emailEditText.setText(snapshot.child(key).child("email").getValue().toString());
                        numberEditText.setText(snapshot.child(key).child("numeroDiTelefono").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            final Button logout=findViewById(R.id.esci);
            logout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    auth.signOut();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            });
            final Button edit=findViewById(R.id.modifica_profilo);
            edit.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), EditActivity.class));
                    finish();
                }
            });
        }
    }

}