package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private StorageReference mStorageRef;
    private Uri imageUri;
    private ImageView image;
    private static int RESULT_LOAD_IMAGE = 1;
    private Contatti c;
    private String nick=null,num=null,mail=null,ph=null;
    private boolean salvato;
    private EditText nickname,numero,email;
    private ArrayList<EditText> extra;
    private ArrayList<String> extras=new ArrayList<String>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        salvato=false;
        auth=FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        image=findViewById(R.id.edit_image);
        String key=auth.getCurrentUser().getUid();
        extra=new ArrayList<EditText>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        LinearLayout l=findViewById(R.id.layout_edit);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                if(snapshot.child(key).child("nickname").getValue()!=null && snapshot.child(key).child("numeroDiTelefono").getValue()!=null
                        && snapshot.child(key).child("email").getValue()!=null && !salvato) {
                    nickname = aggiungiTextField(l, snapshot.child(key).child("nickname").getValue().toString(), InputType.TYPE_CLASS_TEXT);
                    numero = aggiungiTextField(l, snapshot.child(key).child("numeroDiTelefono").getValue().toString(), InputType.TYPE_CLASS_PHONE);
                    email = aggiungiTextField(l, snapshot.child(key).child("email").getValue().toString(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    while (snapshot.child(key).child("extras").child(""+i).getValue() != null) {
                        extra.add(aggiungiTextField(l, snapshot.child(key).child("extras").child(""+i).getValue().toString(), InputType.TYPE_CLASS_TEXT));
                        i++;
                    }
                }
                else if(snapshot.child(key).child("nickname").getValue()==null && snapshot.child(key).child("numeroDiTelefono").getValue()==null
                        && snapshot.child(key).child("email").getValue()==null && !salvato){
                    nickname=aggiungiTextFieldVuoto(l,"Nickname",InputType.TYPE_CLASS_TEXT);
                    numero=aggiungiTextFieldVuoto(l,"Numero di telefono",InputType.TYPE_CLASS_PHONE);
                    email=aggiungiTextField(l, auth.getCurrentUser().getEmail(), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                }
                if(snapshot.child(key).child("foto").getValue()!=null){
                    ph=snapshot.child(key).child("foto").getValue().toString();
                    Glide.with(getBaseContext())
                            .load(Uri.parse(snapshot.child(key).child("foto").getValue().toString())).apply(RequestOptions.circleCropTransform())
                            .into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }

        });
        final Button salva=findViewById(R.id.salva_edit);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvato=true;
                String key=auth.getCurrentUser().getUid();
                String pic=ph;
                nick=nickname.getText().toString().trim();
                num=numero.getText().toString().trim();
                mail=email.getText().toString().trim();
                for(int i=0;i<extra.size();i++) {
                    extras.add(extra.get(i).getText().toString().trim());
                }
                c = new Contatti(pic,nick, num, mail, extras);
                databaseReference.child(key).setValue(c, new DatabaseReference.CompletionListener() {

                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        //finish();
                        Toast.makeText(EditActivity.this, "MODIFICA SALVATA", Toast.LENGTH_SHORT).show();
                    }
                });
                StorageReference riversRef = mStorageRef.child(key).child("foto");
                if(imageUri!=null) {
                    riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    databaseReference.child(key).child("foto").setValue(uri.toString());
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }

        });

        final Button aggiungiEmail=findViewById(R.id.aggiungi_email);

        aggiungiEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extra.add(aggiungiTextFieldVuoto(l,"Email",InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS));
            }
        });
        final Button aggiungiNumero=findViewById(R.id.aggiungi_numero);
        aggiungiNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extra.add(aggiungiTextFieldVuoto(l,"Numero di telefono",InputType.TYPE_CLASS_PHONE));
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
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE){
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }
}