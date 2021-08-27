package com.example.booking3.retrofit;


import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Cliente;
import com.example.booking3.beans.Hotel;
import com.example.booking3.utils.Constantes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private Retrofit retrofit;


    private static final String BASE_URL = Constantes.SERVER_URL+":"+Constantes.SERVER_PORT+"/"+Constantes.SERVER_APP+"/";

    public ApiAdapter(){
/*
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
        okhttp.addInterceptor(loggingInterceptor);
*/
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public Call<ArrayList<Cliente>> getCliente(Cliente cliente){

        ApiService service = retrofit.create(ApiService.class);
        return service.getClienteBY(cliente.getEmail(), cliente.getContrasena());
    }

    public Call<ArrayList<Cliente>> addClient(Cliente cliente){

        ApiService service = retrofit.create(ApiService.class);
        return service.addCliente(cliente.getTelefono(), cliente.getDireccion(), cliente.getEmail(), cliente.getContrasena(), cliente.getDni(), cliente.getApellido(), cliente.getNombre());
    }

    public Call<ArrayList<Hotel>> getHoteles(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getHoteles();
    }

    public Call<ArrayList<Categoria>> getCategorias(){

        ApiService service = retrofit.create(ApiService.class);
        return service.getCategoriaBY();
    }


}