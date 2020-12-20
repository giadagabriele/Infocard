 package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

 public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = auth.getCurrentUser().getUid();
        final TextView nicknameEditText = findViewById(R.id.contatto_nickname);
        final TextView emailEditText = findViewById(R.id.contatto_email);
        final TextView numberEditText = findViewById(R.id.contatto_numeroDiTelefono);
        final ImageView profilePic=findViewById(R.id.contatto_foto);
        emailEditText.setText(auth.getCurrentUser().getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(key).child("nickname").getValue()!=null && snapshot.child(key).child("numeroDiTelefono").getValue()!=null
                && snapshot.child(key).child("email").getValue()!=null) {
                    nicknameEditText.setText(snapshot.child(key).child("nickname").getValue().toString());
                    numberEditText.setText(snapshot.child(key).child("numeroDiTelefono").getValue().toString());
                }
                if(snapshot.child(key).child("foto").getValue()!=null){
                    Glide.with(getBaseContext())
                            .load(Uri.parse(snapshot.child(key).child("foto").getValue().toString())).apply(RequestOptions.circleCropTransform())
                            .into(profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final Button indietro = findViewById(R.id.indietro_profilo);
        indietro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                finish();
            }
        });
        final Button edit = findViewById(R.id.modifica_profilo);
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditActivity.class));
                finish();
            }
        });

    }

}