package com.example.booking3.presenter;

import com.example.booking3.beans.Hotel;
import com.example.booking3.interfaces.HotelContract;
import com.example.booking3.model.HotelModel;

import java.util.ArrayList;

public class HotelPresenter implements HotelContract.Presenter {

    private HotelContract.View vista;
    private HotelContract.Model model;

    public HotelPresenter(HotelContract.View vista) {
        this.vista = vista;
        this.model = new HotelModel();
    }

    @Override
    public void getHotel() {
        model.getHotelService(new HotelContract.Model.OnLstHotelesListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Hotel> lstHoteles) {
                vista.sucessLstHotel(lstHoteles);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstHotel(error);
            }
        });
    }
}
