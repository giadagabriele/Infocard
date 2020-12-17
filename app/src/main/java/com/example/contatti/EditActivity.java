package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Contatti c;
    private String nick=null,num=null,mail=null;
    private EditText nickname,numero,email,extra;
    private ArrayList<String> extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        auth=FirebaseAuth.getInstance();
        String key=auth.getCurrentUser().getUid();
        extras=new ArrayList<String>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        LinearLayout l=findViewById(R.id.layout_edit);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(key).child("nickname").getValue()!=null && snapshot.child(key).child("numeroDiTelefono").getValue()!=null
                        && snapshot.child(key).child("email").getValue()!=null) {
                    nickname = aggiungiTextField(l, snapshot.child(key).child("nickname").getValue().toString(), InputType.TYPE_CLASS_TEXT);
                    numero = aggiungiTextField(l, snapshot.child(key).child("numeroDiTelefono").getValue().toString(), InputType.TYPE_CLASS_PHONE);
                    email = aggiungiTextField(l, snapshot.child(key).child("email").getValue().toString(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                }
                else{
                    nickname=aggiungiTextFieldVuoto(l,"Nickname",InputType.TYPE_CLASS_TEXT);
                    numero=aggiungiTextFieldVuoto(l,"Numero di telefono",InputType.TYPE_CLASS_PHONE);
                    email=aggiungiTextField(l, auth.getCurrentUser().getEmail(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final Button salva=findViewById(R.id.salva_edit);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=auth.getCurrentUser().getUid();
                nick=nickname.getText().toString().trim();
                num=numero.getText().toString().trim();
                mail=email.getText().toString().trim();
                if(extra!=null) {
                    extras.add(extra.getText().toString().trim());
                }
                c = new Contatti(nick, num, mail, extras);
                databaseReference.child(key).setValue(c, new DatabaseReference.CompletionListener() {

                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        Toast.makeText(EditActivity.this, "MODIFICA SALVATA", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        final Button aggiungiEmail=findViewById(R.id.aggiungi_email);

        aggiungiEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extra=aggiungiTextFieldVuoto(l,"Email",InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        });
        final Button aggiungiNumero=findViewById(R.id.aggiungi_numero);
        aggiungiNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extra=aggiungiTextFieldVuoto(l,"Numero di telefono",InputType.TYPE_CLASS_PHONE);
            }
        });
        final Button indietro=findViewById(R.id.indietro_edit);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }
        });
    }
    private EditText aggiungiTextField(LinearLayout l, String text, int inputType){
        EditText t=new EditText(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t.setLayoutParams(lp);
        t.setText(text);
        t.setEms(10);
        t.setInputType(inputType);
        t.setGravity(Gravity.CENTER);
        l.addView(t);
        return t;
    }
    private EditText aggiungiTextFieldVuoto(LinearLayout l, String hint, int inputType){
        EditText t=new EditText(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t.setLayoutParams(lp);
        t.setHint(hint);
        t.setEms(10);
        t.setInputType(inputType);
        t.setGravity(Gravity.CENTER);
        l.addView(t);
        return t;
    }
}