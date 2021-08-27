package com.example.booking3.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booking3.R;
import com.example.booking3.beans.Hotel;
import com.google.gson.Gson;

public class ViewHotelActivity extends AppCompatActivity {

    private TextView hotel_name, direccion, categoria, habitaciones, votos, puntuacion;
    public RatingBar estrellitas;
    public ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        String jsonHotel = getIntent().getStringExtra("HOTEL_DATA");
        Gson gson = new Gson();
        Hotel hotel = gson.fromJson(jsonHotel, Hotel.class);

        initComponents();

        hotel_name.setText(hotel.getNombre());
        direccion.setText(hotel.getDireccion());
        categoria.setText(hotel.getCategoria().getNombre());
        habitaciones.setText(Integer.toString(hotel.getHabitaciones()));
        votos.setText(Integer.toString(hotel.getVotos()));
        puntuacion.setText(Integer.toString(hotel.getPuntuacion()));
        estrellitas.setNumStars(5);
        estrellitas.setEnabled(false);
        estrellitas.setRating(hotel.getEstrellas());

    }

    private void initComponents(){
        hotel_name = findViewById(R.id.hotel_name);
        direccion = findViewById(R.id.direccion);
        categoria = findViewById(R.id.categoria);
        habitaciones = findViewById(R.id.habitaciones);
        votos = findViewById(R.id.votos);
        puntuacion = findViewById(R.id.puntuacion);
        estrellitas = findViewById(R.id.estrellitas);
    }

}
