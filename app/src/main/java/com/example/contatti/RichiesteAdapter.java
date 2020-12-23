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

import java.util.ArrayList;
import java.util.List;

public class RichiesteAdapter extends RecyclerView.Adapter<RichiesteAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView nickname;
        CardView cardView;
        LinearLayout vlayout;
        Button accetta;

        ViewHolder(View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.richiesta_foto);
            nickname = itemView.findViewById(R.id.richiesta_nickname);
            cardView = itemView.findViewById(R.id.cardview_richiesta);
            vlayout = itemView.findViewById(R.id.valori_richiesta);
            accetta=itemView.findViewById(R.id.accetta);
        }
    }
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private List<Contatti> contatti;
    private LayoutInflater inflater;
    private Context context;

    RichiesteAdapter(Context context, List<Contatti> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.contatti = data;
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
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
        holder.accetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "RICHIESTA ACCETTATA", Toast.LENGTH_SHORT).show();
                RichiesteActivity.codaAccettate.add(contatto.getUID());
                databaseReference.child(auth.getCurrentUser().getUid()).child("accettate").setValue(RichiesteActivity.codaAccettate);
                HomeProfileActivity.codaRichieste.remove(contatto.getUID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contatti.size();
    }
}
