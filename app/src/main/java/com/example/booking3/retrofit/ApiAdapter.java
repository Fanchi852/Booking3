package com.example.booking3.retrofit;


import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Cliente;
import com.example.booking3.beans.Habitacion;
import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Puntuacion;
import com.example.booking3.beans.Reserva;
import com.example.booking3.beans.ReservaHabitacion;
import com.example.booking3.utils.Constantes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private Retrofit retrofit;

    private static final String BASE_URL = Constantes.SERVER_URL+":"+Constantes.SERVER_PORT+"/"+Constantes.SERVER_APP+"/";

    public ApiAdapter(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    /*---- CLIENTES ----*/

    public Call<ArrayList<Cliente>> getCliente(Cliente cliente){

        ApiService service = retrofit.create(ApiService.class);
        return service.getClienteBY(cliente.getEmail(), cliente.getContrasena());
    }

    public Call<Boolean> addClient(Cliente cliente){

        ApiService service = retrofit.create(ApiService.class);
        return service.addCliente(cliente.getTelefono(), cliente.getDireccion(), cliente.getEmail(), cliente.getContrasena(), cliente.getDni(), cliente.getApellido(), cliente.getNombre());
    }

    /*---- HOTELES ----*/

    public Call<ArrayList<Hotel>> getHoteles(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getHoteles();
    }

    /*---- CATEGORIA ----*/

    public Call<ArrayList<Categoria>> getCategorias(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getCategoriaALL();
    }

    /*---- RESERVAS ----*/

    public Call<ArrayList<Reserva>> getReservas(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getReservaAll();
    }

    public Call<ArrayList<Reserva>> getReservasBY(Reserva reserva){

        ApiService service = retrofit.create(ApiService.class);
        return service.getReservaBY(reserva.getId_reserva());
    }

    public Call<ArrayList<Reserva>> addReservas(Reserva reserva){

        ApiService service = retrofit.create(ApiService.class);
        return service.addReserva(reserva.getId_cliente(), reserva.getId_hotel(), reserva.getHabitaciones(), reserva.getFecha_entrada(), reserva.getFecha_salida());
    }

    /*---- RESERVASHABITACION ----*/

    public Call<ArrayList<ReservaHabitacion>> getReservasHabitaciones(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getReservaHabitacionAll();
    }

    public Call<ArrayList<ReservaHabitacion>> getReservasHabitacionesBY(ReservaHabitacion reservaHabitacion){

        ApiService service = retrofit.create(ApiService.class);
        return service.getReservaHabitacionBY(reservaHabitacion.getId_reservahabitacion());
    }

    /*---- HABITACION ----*/

    public Call<ArrayList<Habitacion>> getHabitaciones(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getHabitacionAll();
    }

    public Call<ArrayList<Habitacion>> getHabitacionesBY(Habitacion habitacion){

        ApiService service = retrofit.create(ApiService.class);
        return service.getHabitacionBY(habitacion.getId_habitacion());
    }

    /*---- PUNTUACION ----*/

    public Call<ArrayList<Puntuacion>> getPuntuaciones(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getPuntuacionAll();
    }

    public Call<ArrayList<Puntuacion>> getPuntuacionesBY(Puntuacion puntuacion){

        ApiService service = retrofit.create(ApiService.class);
        return service.getPuntuacionBY(puntuacion.getId_puntuacion());
    }

}