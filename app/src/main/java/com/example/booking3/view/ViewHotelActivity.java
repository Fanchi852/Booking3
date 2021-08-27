package com.example.booking3.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking3.R;
import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Reserva;
import com.example.booking3.beans.ReservaHabitacion;
import com.example.booking3.interfaces.ReservaContract;
import com.example.booking3.interfaces.ReservaHabitacionContract;
import com.example.booking3.presenter.ReservaPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewHotelActivity extends AppCompatActivity implements ReservaContract.View {

    private TextView hotel_name, direccion, categoria, habitaciones, votos, puntuacion, habitacionesDisponibles, numerohabitacionesreserva, fechasalidareserva, fechaentradareserva;
    private RatingBar estrellitas;
    private ImageView imagen;
    private Button btfiltro;

    private Hotel hotel;
    private ReservaPresenter reservaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        Integer id_cliente = getIntent().getIntExtra("id_cliente", 0);
        String jsonHotel = getIntent().getStringExtra("HOTEL_DATA");
        Gson gson = new Gson();
        hotel = gson.fromJson(jsonHotel, Hotel.class);

        initComponents();


        habitacionesDisponibles.setText(String.valueOf(hotel.getHabitacionesDisponibles()));
        hotel_name.setText(hotel.getNombre());
        direccion.setText(hotel.getDireccion());
        categoria.setText(hotel.getCategoria().getNombre());
        habitaciones.setText(Integer.toString(hotel.getHabitaciones()));
        votos.setText(Integer.toString(hotel.getVotos()));
        puntuacion.setText(Integer.toString(hotel.getPuntuacion()));
        estrellitas.setNumStars(5);
        estrellitas.setEnabled(false);
        estrellitas.setRating(hotel.getEstrellas());

        btfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Reserva reserva = new Reserva();

                reserva.setHabitaciones(Integer.valueOf(numerohabitacionesreserva.getText().toString()));
                reserva.setFecha_salida(fechasalidareserva.getText().toString());
                reserva.setFecha_entrada(fechaentradareserva.getText().toString());
                reserva.setId_hotel(hotel.getId_hotel());
                reserva.setId_cliente(id_cliente);

                reservaPresenter.addReserva(reserva);

            }
        });

    }

    private void initComponents(){
        hotel_name = findViewById(R.id.hotel_name);
        direccion = findViewById(R.id.direccion);
        categoria = findViewById(R.id.categoria);
        habitaciones = findViewById(R.id.habitaciones);
        votos = findViewById(R.id.votos);
        puntuacion = findViewById(R.id.puntuacion);
        estrellitas = findViewById(R.id.estrellitas);
        habitacionesDisponibles = findViewById(R.id.habitacionesDisponibles);
        btfiltro = findViewById(R.id.btfiltro);
        numerohabitacionesreserva = findViewById(R.id.numerohabitacionesreserva);
        fechasalidareserva= findViewById(R.id.fechasalidareserva);
        fechaentradareserva = findViewById(R.id.fechaentradareserva);

        reservaPresenter = new ReservaPresenter(this);
    }

    @Override
    public void sucessLstReservas(ArrayList<Reserva> lstReservas) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("insercion realizada con exito");
        builder.setTitle("-- EXITO --");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void failureLstReservas(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("-- ERROR --");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onInitLoading() {

    }
}
