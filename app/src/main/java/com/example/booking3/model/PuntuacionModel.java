package com.example.booking3.model;

import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Puntuacion;
import com.example.booking3.interfaces.PuntuacionContract;
import com.example.booking3.retrofit.ApiAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PuntuacionModel implements PuntuacionContract.Model {
    @Override
    public void getPuntuacionService(OnLstPuntuacionesListener puntuacionListener) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<Puntuacion>> request = apiclient.getPuntuaciones();

        request.enqueue(new Callback<ArrayList<Puntuacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Puntuacion>> call, Response<ArrayList<Puntuacion>> response) {
                puntuacionListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Puntuacion>> call, Throwable t) {
                puntuacionListener.onFailure(t.getStackTrace().toString());
            }
        });

    }

    @Override
    public void getPuntuacionServiceBY(OnLstPuntuacionesListener puntuacionListener, Puntuacion puntuacion) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<Puntuacion>> request = apiclient.getPuntuacionesBY(puntuacion);

        request.enqueue(new Callback<ArrayList<Puntuacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Puntuacion>> call, Response<ArrayList<Puntuacion>> response) {
                puntuacionListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Puntuacion>> call, Throwable t) {
                puntuacionListener.onFailure(t.getStackTrace().toString());
            }
        });
    }
}
