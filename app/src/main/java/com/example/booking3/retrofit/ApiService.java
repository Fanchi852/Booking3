package com.example.booking3.retrofit;


import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Cliente;
import com.example.booking3.beans.Habitacion;
import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Puntuacion;
import com.example.booking3.beans.Reserva;
import com.example.booking3.beans.ReservaHabitacion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    /*---- CLIENTES ----*/

    @GET("Controller?ACTION=CLIENTE.FIND_BY")
    Call<ArrayList<Cliente>> getClienteBY(
            @Query("email") String email,
            @Query("contrasena") String contrasena
    );

    @GET("Controller?ACTION=CLIENTE.ADD")
    Call<Object> addCliente(
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
    Call<ArrayList<Categoria>> getCategoriaALL();

    /*---- RESERVAS ----*/

    @GET("Controller?ACTION=RESERVA.FIND_ALL")
    Call<ArrayList<Reserva>> getReservaAll();

    @GET("Controller?ACTION=RESERVA.FIND_BY")
    Call<ArrayList<Reserva>> getReservaBY(
            @Query("id_reserva") Integer id_reserva
            );

    @GET("Controller?ACTION=RESERVA.ADD")
    Call<Object> addReserva(
            @Query("ID_CLIENTE") Integer id_cliente,
            @Query("ID_HOTELA") Integer id_hotel,
            @Query("HABITACIONES") Integer habitaciones,
            @Query("FECHA_ENTRADA") String fecha_entrada,
            @Query("FECHA_SALIDA") String fecha_salida,
            @Query("RESERVAS_HABITACIONES") List<ReservaHabitacion> reservas_habitaciones
    );

    /*---- RESERVASHABITACIONES ----*/

    @GET("Controller?ACTION=RESERVAHABITACION.FIND_ALL")
    Call<ArrayList<ReservaHabitacion>> getReservaHabitacionAll();

    @GET("Controller?ACTION=RESERVAHABITACION.FIND_BY")
    Call<ArrayList<ReservaHabitacion>> getReservaHabitacionBY(
            @Query("id_reservahabitacion") Integer id_reservahabitacion
    );

    /*---- HABITACIONES ----*/

    @GET("Controller?ACTION=HABITACION.FIND_ALL")
    Call<ArrayList<Habitacion>> getHabitacionAll();

    @GET("Controller?ACTION=HABITACION.FIND_BY")
    Call<ArrayList<Habitacion>> getHabitacionBY(
            @Query("id_habitacion") Integer id_habitacion
    );

    /*---- PUNTUACIONES ----*/

    @GET("Controller?ACTION=PUNTUACION.FIND_ALL")
    Call<ArrayList<Puntuacion>> getPuntuacionAll();

    @GET("Controller?ACTION=PUNTUACION.FIND_BY")
    Call<ArrayList<Puntuacion>> getPuntuacionBY(
            @Query("id_puntuacion") Integer id_puntuacion
    );
}
