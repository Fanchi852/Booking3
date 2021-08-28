package com.example.booking3.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.booking3.R;
import com.example.booking3.beans.Habitacion;
import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Reserva;
import com.example.booking3.beans.ReservaHabitacion;
import com.example.booking3.interfaces.ReservaContract;
import com.example.booking3.presenter.ReservaPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewHotelActivity extends AppCompatActivity implements ReservaContract.View {

    private TextView hotel_name, direccion, categoria, habitaciones, votos, puntuacion, habitacionesDisponibles, precioreserva, fechasalidareserva, fechaentradareserva;
    private RatingBar estrellitas;
    private ImageView imagen;
    private Button btfiltro, buttonseleccionhabitaciones;
    private ProgressBar progressBar;

    ViewHotelActivity myself;


    private Hotel hotel;
    private ReservaPresenter reservaPresenter;

    private List<Integer> habitacionesSeleccionadasIndex;
    private List<Habitacion> habitacionesSeleccionadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        myself = this;

        Integer id_cliente = getIntent().getIntExtra("id_cliente", 0);
        String jsonHotel = getIntent().getStringExtra("HOTEL_DATA");
        Gson gson = new Gson();
        Map<String,String> fechas = new HashMap<>();
        hotel = gson.fromJson(jsonHotel, Hotel.class);

        initComponents();

        habitacionesSeleccionadasIndex = new ArrayList<>();
        habitacionesSeleccionadas = new ArrayList<>();

        habitacionesDisponibles.setText(String.valueOf(hotel.getHabitacionesDisponibles(fechas)));
        hotel_name.setText(hotel.getNombre());
        direccion.setText(hotel.getDireccion());
        categoria.setText(hotel.getCategoria().getNombre());
        habitaciones.setText(Integer.toString(hotel.getHabitaciones()));
        votos.setText(Integer.toString(hotel.getVotos()));
        puntuacion.setText(Integer.toString(hotel.getPuntuacion()));
        estrellitas.setNumStars(5);
        estrellitas.setEnabled(false);
        estrellitas.setRating(hotel.getEstrellas());

        fechasalidareserva.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("Preparado para editar");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("Editando");

                Map<String,String> fechas = new HashMap<>();
                fechas.put("fecha_entrada", fechaentradareserva.getText().toString());
                fechas.put("fecha_salida", fechasalidareserva.getText().toString());
                habitacionesDisponibles.setText(String.valueOf(hotel.getHabitacionesDisponibles(fechas)));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("Editado");
            }
        });

        fechaentradareserva.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("Preparado para editar");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("Editando");

                System.out.println("i: " + i);
                System.out.println("i1: " + i1);
                System.out.println("i2: " + i2);

                Map<String,String> fechas = new HashMap<>();
                fechas.put("fecha_entrada", fechaentradareserva.getText().toString());
                fechas.put("fecha_salida", fechasalidareserva.getText().toString());
                habitacionesDisponibles.setText(String.valueOf(hotel.getHabitacionesDisponibles(fechas)));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("Editado");
            }
        });

        buttonseleccionhabitaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitacionesSeleccionadasIndex.clear();
                habitacionesSeleccionadas.clear();

                AlertDialog.Builder builder = new AlertDialog.Builder(myself);

                List<Habitacion> habitacionesHotel = hotel.getListHabitaciones();
                List<Reserva> reservasHotel = hotel.getReservas();

                Map<Integer, Habitacion> habitacionesHotelMap = new HashMap<>();
                for(Habitacion habitacion: habitacionesHotel){
                    habitacionesHotelMap.put(habitacion.getId_habitacion(), habitacion);
                }

                for(Reserva reserva: reservasHotel){
                    for(ReservaHabitacion reservaHabitacion: reserva.getReservasHabitaciones()){
                        if(habitacionesHotelMap.containsKey(reservaHabitacion.getId_habitacion())){
                            habitacionesHotelMap.remove(reservaHabitacion.getId_habitacion());
                        }
                    }
                }

                String[] arrayOptions = new String[habitacionesHotelMap.keySet().size()];
                Integer index = 0;
                for(Habitacion habitacion: habitacionesHotelMap.values()){
                    arrayOptions[index] = habitacion.getNombre() + ": " + habitacion.getPrecio() + "€";
                    index++;
                }

                builder.setTitle("Seleccionar habitaciones")
                        .setMultiChoiceItems(arrayOptions, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            habitacionesSeleccionadasIndex.add(which);
                                        } else if (habitacionesSeleccionadasIndex.contains(which)) {
                                            habitacionesSeleccionadasIndex.remove(which);
                                        }
                                    }
                                })
                        // Set the action buttons
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                List<Habitacion> habs = new ArrayList<>(habitacionesHotelMap.values());
                                Double precioTotal = 0.0;
                                for(Integer index: habitacionesSeleccionadasIndex){
                                    Habitacion habitacion = habs.get(index);
                                    habitacionesSeleccionadas.add(habitacion);
                                    precioTotal += habitacion.getPrecio();
                                }
                                precioreserva.setText(String.valueOf(precioTotal));
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                habitacionesSeleccionadasIndex.clear();
                                habitacionesSeleccionadas.clear();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        btfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(habitacionesSeleccionadas.isEmpty()){
                    showMessageOnFinish("Error", "Es necesario seleccionar habitaciones.");
                    return;
                }

                List<ReservaHabitacion> reservasHabitaciones = new ArrayList<>();
                for(Habitacion habitacion: habitacionesSeleccionadas){
                    ReservaHabitacion reservaHabitacion = new ReservaHabitacion();
                    reservaHabitacion.setId_habitacion(habitacion.getId_habitacion());
                    reservasHabitaciones.add(reservaHabitacion);
                }

                Reserva reserva = new Reserva();

                reserva.setFecha_salida(fechasalidareserva.getText().toString());
                reserva.setFecha_entrada(fechaentradareserva.getText().toString());
                reserva.setId_hotel(hotel.getId_hotel());
                reserva.setId_cliente(id_cliente);
                reserva.setHabitaciones(reservasHabitaciones.size());
                reserva.setReservasHabitaciones(reservasHabitaciones);

                System.out.println("reserva de habitacion saliente"+reservasHabitaciones.toString());

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
        precioreserva = findViewById(R.id.precioreserva);
        fechasalidareserva= findViewById(R.id.fechasalidareserva);
        fechaentradareserva = findViewById(R.id.fechaentradareserva);
        progressBar = findViewById(R.id.progressBar);
        buttonseleccionhabitaciones = findViewById(R.id.buttonseleccionhabitaciones);

        reservaPresenter = new ReservaPresenter(this);
    }

    @Override
    public void sucessLstReservas(ArrayList<Reserva> lstReservas) {
        showMessageOnFinish("Success", "Reserva registrada con exito");
    }

    @Override
    public void failureLstReservas(String message) {
        showMessageOnFinish("Error", "Error al registrar la reserva");
    }

    @Override
    public void onInitLoading() {
        System.out.println("--------------------- LOADING------------------");
        progressBar.setMax(10);
        progressBar.setProgress(0);

        /* GESTION DE CARGA */
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showMessageOnFinish(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();

        /* GESTION DE CARGA */
        progressBar.setVisibility(View.GONE);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Intent intent = new Intent(getApplicationContext(), HotelListActivity.class);
                startActivity(intent);
            }
        });

    }
}
