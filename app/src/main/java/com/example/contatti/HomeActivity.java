package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.contatti.ui.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        Context context=this;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        } else {
            ArrayList<Contatti> contatti = new ArrayList<Contatti>();
            String key=auth.getCurrentUser().getUid();
            final ImageView profilo = findViewById(R.id.profilo);
            final TextView nicknameEditText = findViewById(R.id.home_nickname);
            final TextView emailEditText = findViewById(R.id.home_email);
            final TextView numberEditText = findViewById(R.id.home_numeroDiTelefono);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(key).child("foto").getValue()!=null){
                        Glide.with(getBaseContext())
                                .load(Uri.parse(snapshot.child(key).child("foto").getValue().toString())).apply(RequestOptions.circleCropTransform())
                                .into(profilo);
                    }
                    for(DataSnapshot d:snapshot.getChildren()) {
                        int i=0;
                        ArrayList<String> extras=new ArrayList<String>();
                        if(!d.getKey().equals(key)) {
                            if (snapshot.child(d.getKey()).child("nickname").getValue() != null && snapshot.child(d.getKey()).child("numeroDiTelefono").getValue() != null
                                    && snapshot.child(d.getKey()).child("email").getValue()!=null && snapshot.child(d.getKey()).child("foto").getValue()!=null) {
                                while (snapshot.child(d.getKey()).child("extras").child(""+i).getValue() != null) {
                                    extras.add(snapshot.child(d.getKey()).child("extras").child(""+i).getValue().toString());
                                    i++;
                                }
                                contatti.add(new Contatti(snapshot.child(d.getKey()).child("foto").getValue().toString(),snapshot.child(d.getKey()).child("nickname").getValue().toString(), snapshot.child(d.getKey()).child("numeroDiTelefono").getValue().toString(), snapshot.child(d.getKey()).child("email").getValue().toString(), extras));
                            }
                        }
                    }
                    recyclerView = findViewById(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    adapter = new HomeAdapter(context, contatti);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            profilo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    finish();

                }
            });
            final ImageView logout = findViewById(R.id.esci_home);
            logout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    auth.signOut();
                    startActivity(new Intent(getApplicationContext(), Login.class));

                    finish();
                }
            });
        }
    }
}