package com.example.booking3.model;

import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Hotel;
import com.example.booking3.interfaces.HotelContract;
import com.example.booking3.retrofit.ApiAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelModel implements HotelContract.Model {
    private OnLstHotelesListener onLstHotelesListener;
    private ArrayList<Hotel> lstHotels = new ArrayList<>();

    @Override
    public void getHotelService(OnLstHotelesListener hotelListener) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<Hotel>> request = apiclient.getHoteles();

        hotelListener.onInitLoading();
        request.enqueue(new Callback<ArrayList<Hotel>>() {
            @Override
            public void onResponse(Call<ArrayList<Hotel>> call, Response<ArrayList<Hotel>> response) {
                hotelListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Hotel>> call, Throwable t) {
                hotelListener.onFailure(t.getMessage());
            }
        });
    }
}
