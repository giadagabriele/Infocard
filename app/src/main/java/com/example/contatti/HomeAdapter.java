package com.example.contatti;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    boolean prima=false;
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
        holder.numeroDiTelefono.setText(contatto.getNumeroDiTelefono());
        holder.email.setText(contatto.getEmail());
        Glide.with(holder.itemView)
                .load(Uri.parse(contatto.getFoto())).apply(RequestOptions.circleCropTransform())
                .into(holder.photo);
        List<String> extras = contatto.getExtras();
        for (int i = 0; i < extras.size(); i++) {
            if(!prima) {
                holder.vlayout.addView(createTextView(extras.get(i)));
                if(i==extras.size()-1){
                    prima=true;
                }
            }
        }
    }

    private TextView createTextView(String text) {
        TextView t = new TextView(context);
        t.setText(text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t.setTextSize(15);
        params.setMargins(38,40,0,0);
        t.setLayoutParams(params);
        return t;
    }

    @Override
    public int getItemCount() {
        return contatti.size();
    }


}
