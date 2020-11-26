 package com.example.contatti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.contatti.ui.login.Login;
import com.google.firebase.auth.FirebaseAuth;

 public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        else {
            setContentView(R.layout.activity_main);
            final Button logout=findViewById(R.id.esci);
            logout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    auth.signOut();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            });
            final Button edit=findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), EditActivity.class));
                    finish();
                }
            });
        }
    }

}