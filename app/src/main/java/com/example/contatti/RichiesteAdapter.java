package com.example.contatti;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RichiesteAdapter extends RecyclerView.Adapter<RichiesteAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView nickname;
        CardView cardView;
        LinearLayout vlayout;
        Button accetta,rifiuta;

        ViewHolder(View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.richiesta_foto);
            nickname = itemView.findViewById(R.id.richiesta_nickname);
            cardView = itemView.findViewById(R.id.cardview_richiesta);
            vlayout = itemView.findViewById(R.id.valori_richiesta);
            accetta=itemView.findViewById(R.id.accetta);
            rifiuta=itemView.findViewById(R.id.rifiuta);
        }
    }
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private List<Contatti> contatti;
    private LayoutInflater inflater;
    private Context context;
    private boolean ok_,okk_;

    RichiesteAdapter(Context context, List<Contatti> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.contatti = data;
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        ok_=true;
        okk_=true;
    }


    @NonNull
    @Override
    public RichiesteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_coda_richieste, parent, false);
        return new RichiesteAdapter.ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RichiesteAdapter.ViewHolder holder, int position) {
        Contatti contatto = contatti.get(position);
        holder.nickname.setText(contatto.getNickname());
        Glide.with(holder.itemView)
                .load(Uri.parse(contatto.getFoto())).apply(RequestOptions.circleCropTransform())
                .into(holder.photo);
        boolean ok=true;
        for(int i=0;i<RichiesteActivity.codaAccettate.size();i++){
            if(contatto.getUID().equals(RichiesteActivity.codaAccettate.get(i))){
                holder.accetta.setEnabled(false);
                holder.rifiuta.setEnabled(false);
                if(ok) {
                    holder.vlayout.addView(createTextView("RICHIESTA ARCHIVIATA: ACCETTATA"));
                    ok=false;
                }
            }
        }
        boolean okk=true;
        for(int i=0;i<RichiesteActivity.codaRifiutate.size();i++){
            if(contatto.getUID().equals(RichiesteActivity.codaRifiutate.get(i))){
                holder.accetta.setEnabled(false);
                holder.rifiuta.setEnabled(false);
                if(okk) {
                    holder.vlayout.addView(createTextView("RICHIESTA ARCHIVIATA: RIFIUTATA"));
                    okk=false;
                }
            }
        }
        holder.accetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ok_) {
                    ok_ = false;
                    Toast.makeText(context, "RICHIESTA ACCETTATA", Toast.LENGTH_SHORT).show();
                    RichiesteActivity.codaAccettate.add(contatto.getUID());
                    for(int i=0;i<RichiesteActivity.codaAccettate.size();i++){
                        for(int j=0;j<RichiesteActivity.codaAccettate.size();j++){
                            if(i!=j) {
                                if (RichiesteActivity.codaAccettate.get(i).equals(RichiesteActivity.codaAccettate.get(j))){
                                    RichiesteActivity.codaAccettate.remove(RichiesteActivity.codaAccettate.get(j));
                                }
                            }
                        }
                    }
                    databaseReference.child(auth.getCurrentUser().getUid()).child("accettate").setValue(RichiesteActivity.codaAccettate);
                }
            }
        });
        holder.rifiuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(okk_) {
                    okk_=false;
                    Toast.makeText(context, "RICHIESTA RIFIUTATA", Toast.LENGTH_SHORT).show();
                    RichiesteActivity.codaRifiutate.add(contatto.getUID());
                    for(int i=0;i<RichiesteActivity.codaRifiutate.size();i++){
                        for(int j=0;j<RichiesteActivity.codaRifiutate.size();j++){
                            if(i!=j) {
                                if (RichiesteActivity.codaRifiutate.get(i).equals(RichiesteActivity.codaRifiutate.get(j))){
                                    RichiesteActivity.codaRifiutate.remove(RichiesteActivity.codaRifiutate.get(j));
                                }
                            }
                        }
                    }
                    databaseReference.child(auth.getCurrentUser().getUid()).child("rifiutate").setValue(RichiesteActivity.codaRifiutate);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contatti.size();
    }
    private TextView createTextView(String text) {
        TextView t = new TextView(context);
        t.setText(text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t.setTextSize(15);
        params.setMargins(20,20,0,20);
        t.setLayoutParams(params);
        return t;
    }
}
