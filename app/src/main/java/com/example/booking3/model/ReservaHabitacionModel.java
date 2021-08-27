package com.example.booking3.model;

import com.example.booking3.beans.Reserva;
import com.example.booking3.beans.ReservaHabitacion;
import com.example.booking3.interfaces.ReservaHabitacionContract;
import com.example.booking3.retrofit.ApiAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaHabitacionModel implements ReservaHabitacionContract.Model {
    @Override
    public void getReservaHabitacionService(OnLstReservasHabitacionesListener reservaHabitacionListener) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<ReservaHabitacion>> request = apiclient.getReservasHabitaciones();

        request.enqueue(new Callback<ArrayList<ReservaHabitacion>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservaHabitacion>> call, Response<ArrayList<ReservaHabitacion>> response) {
                reservaHabitacionListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ReservaHabitacion>> call, Throwable t) {
                reservaHabitacionListener.onFailure(t.getStackTrace().toString());
            }
        });

    }
}
