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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Contatti c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        auth=FirebaseAuth.getInstance();
        final Button salva=findViewById(R.id.salva_edit);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nicknameEditText = findViewById(R.id.edit_nickname);
                final EditText numberEditText = findViewById(R.id.edit_numero);
                String Email=encodeUserEmail(auth.getCurrentUser().getEmail());
                String Nick=nicknameEditText.getText().toString().trim();
                String Num=numberEditText.getText().toString().trim();
                c=new Contatti(Nick,Num);
                databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child(Email).setValue(c, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
                Toast.makeText(EditActivity.this, "MODIFICA SALVATA", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout l=findViewById(R.id.layout_edit);
        /*for(int i=0;i<4;i++){   //mi richiamo i dati dal db e aggiungi su ognuno
            aggiungiTextField(l,"inserisci email",InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }*/
        final Button aggiungiEmail=findViewById(R.id.aggiungi_email);

        aggiungiEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungiTextFieldVuoto(l,"Email",InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        });
        final Button aggiungiNumero=findViewById(R.id.aggiungi_numero);
        aggiungiNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungiTextFieldVuoto(l,"Numero di telefono",InputType.TYPE_CLASS_PHONE);
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
    /*private void aggiungiTextField(LinearLayout l, String text, int inputType){
        EditText t=new EditText(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t.setLayoutParams(lp);
        t.setText(text);
        t.setEms(10);
        t.setInputType(inputType);
        t.setGravity(Gravity.CENTER);
        l.addView(t);
    }*/
    private void aggiungiTextFieldVuoto(LinearLayout l, String hint, int inputType){
        EditText t=new EditText(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t.setLayoutParams(lp);
        t.setHint(hint);
        t.setEms(10);
        t.setInputType(inputType);
        t.setGravity(Gravity.CENTER);
        l.addView(t);

    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }
}