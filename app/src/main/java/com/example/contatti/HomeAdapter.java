package com.example.contatti;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    static String key;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView nickname;
        CardView cardView;
        LinearLayout vlayout;

        ViewHolder(View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.home_foto);
            nickname = itemView.findViewById(R.id.home_nickname);
            cardView = itemView.findViewById(R.id.cardview_home);
            vlayout = itemView.findViewById(R.id.valori);
        }
    }

    private List<Contatti> contatti;
    private LayoutInflater inflater;
    private Context context;

    HomeAdapter(Context context, List<Contatti> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.contatti = data;
    }


    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_users, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Contatti contatto = contatti.get(position);
        holder.nickname.setText(contatto.getNickname());
        Glide.with(holder.itemView)
                .load(Uri.parse(contatto.getFoto())).apply(RequestOptions.circleCropTransform())
                .into(holder.photo);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=contatto.getUID();
                context.startActivity(new Intent(context.getApplicationContext(), HomeProfileActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return contatti.size();
    }
    public static String getKeyUID(){
        return key;
    }
}
