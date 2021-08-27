package com.example.booking3.interfaces;

import com.example.booking3.beans.Hotel;

import java.util.ArrayList;

public interface HotelContract {

    public interface View{
        void sucessLstHotel (ArrayList<Hotel> lstHoteles);
        void failureLstHotel (String message );
        void onInitLoading();
    }

    public interface Presenter{
        void getHotel();
    }

    public interface Model{
        interface OnLstHotelesListener{
            void onInitLoading();
            void onFinished(ArrayList<Hotel> lstHoteles);
            void onFailure(String error);
        }
        void getHotelService(OnLstHotelesListener hotelListener);

    }

}
