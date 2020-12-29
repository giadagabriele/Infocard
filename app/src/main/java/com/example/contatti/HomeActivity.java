package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ListView list;
    private ArrayList<String> users;
    static String nickname_clicked;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        users=new ArrayList<String>();
        Context context=this;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        } else {
            ArrayList<Contatti> contatti = new ArrayList<Contatti>();
            String key=auth.getCurrentUser().getUid();
            final ImageView profilo = findViewById(R.id.profilo);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(key).child("foto").getValue()!=null){
                        Glide.with(getBaseContext())
                                .load(Uri.parse(snapshot.child(key).child("foto").getValue().toString())).apply(RequestOptions.circleCropTransform())
                                .into(profilo);
                    }
                    int j=0;
                    ArrayList<String> coda=new ArrayList<String>();
                    for(DataSnapshot d:snapshot.getChildren()) {
                        if (!d.getKey().equals(key)) {
                            while (snapshot.child(d.getKey()).child("accettate").child("" + j).getValue() != null) {
                                if(snapshot.child(d.getKey()).child("accettate").child("" + j).getValue().toString().equals(key)) {
                                    coda.add(d.getKey());
                                }
                                j++;
                            }
                        }
                    }
                    for(int i=0;i<coda.size();i++){
                        if (snapshot.child(coda.get(i)).child("nickname").getValue() != null
                                && snapshot.child(coda.get(i)).child("foto").getValue()!=null) {
                            contatti.add(new Contatti(coda.get(i),snapshot.child(coda.get(i)).child("foto").getValue().toString(),snapshot.child(coda.get(i)).child("nickname").getValue().toString()));
                        }
                    }

                    for(DataSnapshot d:snapshot.getChildren()) {
                        if(!d.getKey().equals(key)) {
                            if (snapshot.child(d.getKey()).child("nickname").getValue() != null && snapshot.child(d.getKey()).child("foto").getValue()!=null) {
                                users.add(snapshot.child(d.getKey()).child("nickname").getValue().toString());
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

            SearchView searchView=(SearchView) findViewById(R.id.cerca_home);
            list=(ListView) findViewById(R.id.lista);
            arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,users);
            list.setAdapter(arrayAdapter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    arrayAdapter.getFilter().filter(newText);
                    return false;
                }
            });
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    nickname_clicked=parent.getItemAtPosition(position).toString();
                    startActivity(new Intent(getApplicationContext(), SearchProfileActivity.class));
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