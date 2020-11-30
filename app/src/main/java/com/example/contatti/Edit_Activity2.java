package com.example.contatti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Edit_Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_2);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser utente=auth.getCurrentUser();
        LinearLayout l=findViewById(R.id.layout_edit);
        aggiungiTextField(l,utente.getEmail(),InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        for(int i=0;i<4;i++){   //mi richiamo i dati dal db e aggiungi su ognuno
            aggiungiTextField(l,"aggiungi 2",InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        final Button aggiungiEmail=findViewById(R.id.aggiungi_email_edit);

        aggiungiEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungiTextFieldVuoto(l,"email2",InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        });
    }
    private void aggiungiTextField(LinearLayout l, String text, int inputType){
        EditText t=new EditText(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lp.gravity=Gravity.CENTER;
        t.setLayoutParams(lp);
        t.setText(text);
        t.setEms(10);
        t.setInputType(inputType);
        t.setGravity(Gravity.CENTER);
        l.addView(t);
    }
    private void aggiungiTextFieldVuoto(LinearLayout l, String hint, int inputType){
        EditText t=new EditText(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lp.gravity=Gravity.CENTER;
        t.setLayoutParams(lp);
        t.setHint(hint);
        t.setEms(10);
        t.setInputType(inputType);
        t.setGravity(Gravity.CENTER);
        l.addView(t);
    }
}