package com.example.booking3.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking3.R;
import com.example.booking3.beans.Hotel;
import com.example.booking3.view.ViewHotelActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelviewHolder> {

    private Activity activity;
    private Bundle clienteData;

    private List<Hotel> lstHoteles;

    public class HotelviewHolder extends RecyclerView.ViewHolder {

        public TextView card_hotel_name, direccion, categoria;
        public ImageView imagen;
        public RatingBar estrellitas;
        public RelativeLayout hotelsimple;

        public HotelviewHolder(View v) {
            super(v);
            card_hotel_name = (TextView) v.findViewById(R.id.hotel_name);
            direccion = (TextView) v.findViewById(R.id.direccion);
            categoria = (TextView) v.findViewById(R.id.categoria_txt);
            estrellitas = (RatingBar) v.findViewById(R.id.estrellitas);
            hotelsimple = (RelativeLayout) v.findViewById(R.id.hotelsimple);
        }
    }

    public HotelAdapter(List<Hotel> lstHoteles, Bundle clienteData, Activity activity) {
        this.lstHoteles = lstHoteles;
        this.clienteData = clienteData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HotelviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_hotel_simple,
                        parent, false);
        return new HotelviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelviewHolder viewHolder, int position) {
        viewHolder.card_hotel_name.setText(lstHoteles.get(position).getNombre());
        viewHolder.direccion.setText(lstHoteles.get(position).getDireccion());
        viewHolder.categoria.setText(lstHoteles.get(position).getCategoria().getNombre());
        viewHolder.estrellitas.setNumStars(5);
        viewHolder.estrellitas.setRating(lstHoteles.get(position).getEstrellas());
        viewHolder.estrellitas.setEnabled(false);
        viewHolder.hotelsimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String hoteltoJson = gson.toJson(lstHoteles.get(position));
                Intent intent = new Intent(activity, ViewHotelActivity.class);
                intent.putExtra("HOTEL_DATA", hoteltoJson);
                intent.putExtra("id_cliente", clienteData.getInt("id_cliente"));
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "transitionnamepicture").toBundle());
                }else {
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstHoteles.size();
    }




}
