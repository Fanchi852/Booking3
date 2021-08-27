package com.example.booking3.retrofit;


import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Cliente;
import com.example.booking3.beans.Hotel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    /*---- CLIENTES ----*/

    @GET("Controller?ACTION=CLIENTE.FIND_BY")
    Call<ArrayList<Cliente>> getClienteBY(
            @Query("email") String email,
            @Query("contrasena") String contrasena
    );

    @GET("Controller?ACTION=CLIENTE.ADD")
    Call<ArrayList<Cliente>> addCliente(
            @Query("TELEFONO") Integer telefono,
            @Query("DIRECCION") String direccion,
            @Query("EMAIL") String email,
            @Query("CONTRASENA") String contrasena,
            @Query("DNI") String dni,
            @Query("APELLIDO") String apellido,
            @Query("NOMBRE") String nombre
    );

    /*---- HOTELES ----*/

    @GET("Controller?ACTION=HOTEL.FIND_ALL")
    Call<ArrayList<Hotel>> getHoteles();



    /*---- CATEGORIA ----*/

    @GET("Controller?ACTION=CATEGORIA.FIND_ALL")
    Call<ArrayList<Categoria>> getCategoriaBY();



}
