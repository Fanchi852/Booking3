package com.example.booking3.model;

import com.example.booking3.beans.Habitacion;
import com.example.booking3.beans.Hotel;
import com.example.booking3.interfaces.HabitacionContract;
import com.example.booking3.retrofit.ApiAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitacionModel implements HabitacionContract.Model {
    @Override
    public void getHabitacionService(OnLstHabitacionListener habitacionListener) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<Habitacion>> request = apiclient.getHabitaciones();

        request.enqueue(new Callback<ArrayList<Habitacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Habitacion>> call, Response<ArrayList<Habitacion>> response) {
                habitacionListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Habitacion>> call, Throwable t) {
                habitacionListener.onFailure(t.getStackTrace().toString());
            }
        });

    }

    @Override
    public void getHabitacionServiceBY(OnLstHabitacionListener habitacionListener, Habitacion habitacion) {

    }
}
