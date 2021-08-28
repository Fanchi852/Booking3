package com.example.booking3.model;

import com.example.booking3.beans.Cliente;
import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Reserva;
import com.example.booking3.interfaces.ReservaContract;
import com.example.booking3.retrofit.ApiAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaModel implements ReservaContract.Model {
    @Override
    public void getReservaService(OnLstReservasListener reservaListener) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<Reserva>> request = apiclient.getReservas();

        request.enqueue(new Callback<ArrayList<Reserva>>() {
            @Override
            public void onResponse(Call<ArrayList<Reserva>> call, Response<ArrayList<Reserva>> response) {
                reservaListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Reserva>> call, Throwable t) {
                System.out.println(t.getStackTrace().toString());
                t.printStackTrace();
                reservaListener.onFailure(t.getStackTrace().toString());
            }
        });
    }

    @Override
    public void getReservaServiceBY(OnLstReservasListener reservaListener, Reserva reserva) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<Reserva>> request = apiclient.getReservasBY(reserva);


        request.enqueue(new Callback<ArrayList<Reserva>>() {
            @Override
            public void onResponse(Call<ArrayList<Reserva>> call, Response<ArrayList<Reserva>> response) {
                reservaListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Reserva>> call, Throwable t) {
                reservaListener.onFailure(t.getStackTrace().toString());
            }
        });
    }

    @Override
    public void addReservaService(OnLstReservasListener reservaListener, Reserva reserva) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<Object> request = apiclient.addReservas(reserva);

        request.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println(response.body());
                Boolean res = (Boolean) response.body();
                if(res){
                    ArrayList<Reserva> reservas = new ArrayList<>();
                    reservas.add(reserva);
                    reservaListener.onFinished(reservas);
                }else{
                    reservaListener.onFailure("Error");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                reservaListener.onFailure(t.getMessage());
            }
        });
    }
}
