package com.example.contatti;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    //Contiene gli elementi della cardview
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView nickname,numeroDiTelefono,email;
        CardView cardView;
        LinearLayout vlayout;

        ViewHolder(View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.home_foto);
            nickname = itemView.findViewById(R.id.home_nickname);
            numeroDiTelefono= itemView.findViewById(R.id.home_numeroDiTelefono);
            email=itemView.findViewById(R.id.home_email);
            cardView = itemView.findViewById(R.id.cardview_home);
            vlayout = itemView.findViewById(R.id.home_view);
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
        ////////////////////////////////////////////////////////////////////////////////
        View view = inflater.inflate(R.layout.activity_home, parent, false);
        ////////////////////////////////////////////////////////////////////////////////
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Contatti contatto = contatti.get(position);
        holder.nickname.setText(contatto.getNickname());
        holder.numeroDiTelefono.setText(contatto.getNumeroDiTelefono());
        holder.email.setText(contatto.getEmail());

        List<String> extras = contatto.getExtras();
        for(String extra : extras) {
            holder.vlayout.addView(createTextView(extra));
        }
    }

    private TextView createTextView(String text) {
        TextView t = new TextView(context);
        t.setText(text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20,15,0,0);
        t.setLayoutParams(params);
        return t;
    }

    @Override
    public int getItemCount() {
        return contatti.size();
    }


}
