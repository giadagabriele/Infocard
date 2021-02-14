package com.example.contatti;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RichiesteActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private boolean ok;
    private RichiesteAdapter adapter;
    private RecyclerView recyclerView;
    static ArrayList<String> codaAccettate;
    static ArrayList<String> codaRifiutate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richieste);
        auth=FirebaseAuth.getInstance();
        Context context=this;
        String key=auth.getCurrentUser().getUid();
        ok=true;
        ArrayList<Contatti> contatti= new ArrayList<Contatti>();
        codaAccettate=new ArrayList<String>();
        codaRifiutate=new ArrayList<String>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int p=0;
                while (snapshot.child(key).child("accettate").child("" + p).getValue() != null) {
                    codaAccettate.add(snapshot.child(key).child("accettate").child("" + p).getValue().toString());
                    p++;
                }
                int q=0;
                while (snapshot.child(key).child("rifiutate").child("" + q).getValue() != null) {
                    codaRifiutate.add(snapshot.child(key).child("rifiutate").child("" + q).getValue().toString());
                    q++;
                }
                for(DataSnapshot d:snapshot.getChildren()) {
                    int i=0;
                    if (!d.getKey().equals(key)) {
                        ArrayList<String> coda = new ArrayList<String>();
                        while (snapshot.child(d.getKey()).child("richieste").child("" + i).getValue() != null) {
                            coda.add(snapshot.child(d.getKey()).child("richieste").child("" + i).getValue().toString());
                            i++;
                        }
                        if(coda.size()>0) {
                            for (int j = 0; j < coda.size(); j++) {
                                if (snapshot.child(d.getKey()).child("richieste").child("" + j).getValue() != null) {
                                    if (snapshot.child(d.getKey()).child("richieste").child("" + j).getValue().toString().equals(key)) {
                                        if (snapshot.child(d.getKey()).child("foto").getValue() != null
                                                && snapshot.child(d.getKey()).child("nickname").getValue() != null) {
                                            contatti.add(new Contatti(d.getKey(), snapshot.child(d.getKey()).child("foto").getValue().toString(),
                                                                                  snapshot.child(d.getKey()).child("nickname").getValue().toString()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(ok) {
                    ok = false;
                    recyclerView = findViewById(R.id.recycler_richieste);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    adapter = new RichiesteAdapter(context, contatti);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final Button indietro = findViewById(R.id.indietro_richieste);
        indietro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }
        });
    }
}
