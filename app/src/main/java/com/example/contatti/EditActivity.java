package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contatti.ui.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class EditActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Contatti c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Button salva=findViewById(R.id.salva_edit);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nicknameEditText = findViewById(R.id.edit_nickname);
                final EditText emailEditText = findViewById(R.id.edit_email);
                final EditText numberEditText = findViewById(R.id.edit_numero);
                String Email=emailEditText.getText().toString().trim();
                String Nick=nicknameEditText.getText().toString().trim();
                String Num=numberEditText.getText().toString().trim();
                c=new Contatti(Nick,Num,Email);
                databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child(c.getNickname()).setValue(c, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
                Toast.makeText(EditActivity.this, "MODIFICA SALVATA", Toast.LENGTH_SHORT).show();
            }
        });

        final Button aggiungiEmail=findViewById(R.id.aggiungi_email);

        aggiungiEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout l=findViewById(R.id.layout);
                EditText t=new EditText(v.getContext());
                RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                //lp.gravity=Gravity.CENTER;
                lp.addRule(RelativeLayout.BELOW,R.id.edit_numero);
                t.setLayoutParams(lp);
                t.setHint("aggiungi email 2");
                t.setEms(10);
                t.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                t.setGravity(Gravity.CENTER);
                l.addView(t);
            }
        });
        final Button aggiungiNumero=findViewById(R.id.aggiungi_numero);
        aggiungiNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dafare
            }
        });
        final Button indietro=findViewById(R.id.indietro_edit);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }
}