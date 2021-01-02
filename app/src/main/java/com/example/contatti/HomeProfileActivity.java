package com.example.contatti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class HomeProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private LinearLayout linearLayout;
    private boolean trovato;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_home);
        auth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        linearLayout=findViewById(R.id.valori_profiloHome);
        String key=HomeAdapter.getKeyUID();
        trovato=false;
        final ImageView profilo = findViewById(R.id.contattoHome_foto);
        final TextView nicknameEditText = findViewById(R.id.contattoHome_nickname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String coda;
                for(DataSnapshot d:snapshot.getChildren()) {
                    int i = 0;
                    if (!d.getKey().equals(auth.getCurrentUser().getUid())) {
                        while (snapshot.child(d.getKey()).child("accettate").child("" + i).getValue() != null) {
                            coda=snapshot.child(d.getKey()).child("accettate").child("" + i).getValue().toString();
                            if(auth.getCurrentUser().getUid().equals(coda) && key.equals(d.getKey()) && !trovato) {
                                trovato=true;
                                if (snapshot.child(key).child("nickname").getValue() != null && snapshot.child(key).child("foto").getValue() != null
                                        && snapshot.child(key).child("nome").getValue()!=null && snapshot.child(key).child("cognome").getValue()!=null
                                        && snapshot.child(key).child("numeroDiTelefono").getValue() != null
                                        && snapshot.child(key).child("email").getValue() != null ) {
                                    nicknameEditText.setText(snapshot.child(key).child("nickname").getValue().toString());
                                    Glide.with(getBaseContext())
                                            .load(Uri.parse(snapshot.child(key).child("foto").getValue().toString())).apply(RequestOptions.circleCropTransform())
                                            .into(profilo);
                                    linearLayout.addView(createTextView(snapshot.child(key).child("nome").getValue().toString()+" "+snapshot.child(key).child("cognome").getValue().toString()));
                                    linearLayout.addView(createTextView(snapshot.child(key).child("numeroDiTelefono").getValue().toString()));
                                    linearLayout.addView(createTextView(snapshot.child(key).child("email").getValue().toString()));
                                    int h = 0;
                                    ArrayList<String> extras = new ArrayList<String>();
                                    while (snapshot.child(key).child("extras").child("" + h).getValue() != null) {
                                        extras.add(snapshot.child(key).child("extras").child("" + h).getValue().toString());
                                        h++;
                                    }
                                    for (int k = 0; k < extras.size(); k++) {
                                        linearLayout.addView(createTextView(extras.get(k)));
                                    }
                                }
                            }
                            i++;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final Button indietro = findViewById(R.id.indietro_profiloHome);
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
        params.setMargins(20, 20, 0, 20);
        t.setLayoutParams(params);
        return t;
    }
}
