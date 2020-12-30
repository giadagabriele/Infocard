 package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

 public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private LinearLayout linearLayout;
    private boolean immagine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = auth.getCurrentUser().getUid();
        immagine=false;
        final TextView nicknameEditText = findViewById(R.id.contatto_nickname);
        final ImageView profilePic=findViewById(R.id.contatto_foto);
        linearLayout=findViewById(R.id.valori_profilo);
        final Button richieste = findViewById(R.id.coda_richieste);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(key).child("foto").getValue()!=null && snapshot.child(key).child("nickname").getValue()!=null && snapshot.child(key).child("nome").getValue()!=null
                        && snapshot.child(key).child("cognome").getValue()!=null && snapshot.child(key).child("numeroDiTelefono").getValue()!=null
                        && snapshot.child(key).child("email").getValue()!=null && !immagine) {
                        immagine=true;
                    Glide.with(getBaseContext())
                            .load(Uri.parse(snapshot.child(key).child("foto").getValue().toString())).apply(RequestOptions.circleCropTransform())
                            .into(profilePic);
                    nicknameEditText.setText(snapshot.child(key).child("nickname").getValue().toString());
                    linearLayout.addView(createTextView(snapshot.child(key).child("nome").getValue().toString()+" "+snapshot.child(key).child("cognome").getValue().toString()));
                    linearLayout.addView(createTextView(snapshot.child(key).child("numeroDiTelefono").getValue().toString()));
                    linearLayout.addView(createTextView(snapshot.child(key).child("email").getValue().toString()));
                    int i=0;
                    ArrayList<String> extras=new ArrayList<String>();
                    while (snapshot.child(key).child("extras").child(""+i).getValue() != null) {
                        extras.add(snapshot.child(key).child("extras").child(""+i).getValue().toString());
                        i++;
                    }
                    for(int j=0;j<extras.size();j++) {
                        linearLayout.addView(createTextView(extras.get(j)));
                    }
                }

                for(DataSnapshot d:snapshot.getChildren()) {
                    int i = 0;
                    if (!d.getKey().equals(key)) {
                        ArrayList<String> coda = new ArrayList<String>();
                        while (snapshot.child(d.getKey()).child("richieste").child("" + i).getValue() != null) {
                            coda.add(snapshot.child(d.getKey()).child("richieste").child("" + i).getValue().toString());
                            i++;
                        }
                        for(int j=0;j<coda.size();j++){
                            if(snapshot.child(d.getKey()).child("richieste").child(""+j).getValue()!=null) {
                                if (snapshot.child(d.getKey()).child("richieste").child("" + j).getValue().toString().equals(key)) {
                                    richieste.setEnabled(true);
                                    richieste.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(getApplicationContext(), RichiesteActivity.class));
                                            finish();
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        final Button indietro = findViewById(R.id.indietro_profilo);
        indietro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

    }
     private TextView createTextView(String text) {
         TextView t = new TextView(this);
         t.setText(text);
         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
         t.setTextSize(15);
         params.setMargins(20,20,0,20);
         t.setLayoutParams(params);
         return t;
     }

}