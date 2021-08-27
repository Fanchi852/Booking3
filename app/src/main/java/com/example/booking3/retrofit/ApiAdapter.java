package com.example.booking3.retrofit;


import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Cliente;
import com.example.booking3.beans.Hotel;
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

    public Call<ArrayList<Cliente>> addClient(Cliente cliente){

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
        return service.getCategoriaBY();
    }

    /*---- RESERVAS ----*/

    public Call<ArrayList<Reserva>> getReservas(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getReservaAll();
    }

    /*---- RESERVAS ----*/

    public Call<ArrayList<ReservaHabitacion>> getReservasHabitaciones(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getReservaHabitacionAll();
    }

}