package com.example.contatti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private LinearLayout linearLayout;
    private boolean trovato;
    private String key;
    private int index=0;
    static ArrayList<String> codaRichieste=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search);
        auth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        linearLayout=findViewById(R.id.valori_profiloSearch);
        trovato=false;
        final Button richieste=findViewById(R.id.richiesta_profiloSearch);
        final ImageView profilo = findViewById(R.id.contattoSearch_foto);
        final TextView nicknameEditText = findViewById(R.id.contattoSearch_nickname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    if (snapshot.child(d.getKey()).child("nickname").getValue().toString().equals(HomeActivity.nickname_clicked)) {
                        key = d.getKey();
                    }
                }
                if (snapshot.child(key).child("nickname").getValue() != null && snapshot.child(key).child("foto").getValue() != null) {
                    nicknameEditText.setText(snapshot.child(key).child("nickname").getValue().toString());
                    Glide.with(getBaseContext())
                            .load(Uri.parse(snapshot.child(key).child("foto").getValue().toString())).apply(RequestOptions.circleCropTransform())
                            .into(profilo);
                }
                ArrayList<String> coda=new ArrayList<String>();
                for (DataSnapshot d : snapshot.getChildren()) {
                    if (!d.getKey().equals(auth.getCurrentUser().getUid())) {
                        int i = 0;
                        while (snapshot.child(d.getKey()).child("accettate").child("" + i).getValue() != null) {
                            if (snapshot.child(d.getKey()).child("accettate").child("" + i).getValue().toString().equals(auth.getCurrentUser().getUid()))
                                coda.add(d.getKey());
                            i++;
                        }
                    }
                }
                for(int j=0;j<coda.size();j++) {
                    if (key.equals(coda.get(j)) && !trovato) {
                        trovato = true;
                        richieste.setEnabled(false);

                        if (snapshot.child(key).child("nome").getValue() != null && snapshot.child(key).child("cognome").getValue() != null
                                && snapshot.child(key).child("numeroDiTelefono").getValue() != null
                                && snapshot.child(key).child("email").getValue() != null) {
                            linearLayout.addView(createTextView(snapshot.child(key).child("nome").getValue().toString() + " " + snapshot.child(key).child("cognome").getValue().toString()));
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        richieste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchProfileActivity.this, "Richiesta inviata", Toast.LENGTH_SHORT).show();
                codaRichieste.add(key);
                databaseReference.child(auth.getCurrentUser().getUid()).child("richieste").setValue(codaRichieste);
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        final Button indietro = findViewById(R.id.indietro_profiloSearch);
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
