package com.example.booking3.interfaces;

import com.example.booking3.beans.Habitacion;
import com.example.booking3.beans.Hotel;

import java.util.ArrayList;

public interface HabitacionContract {

    public interface View{
        void sucessLstHabitacion (ArrayList<Habitacion> lstHabitaciones);
        void failureLstHabitacion (String message );
        void onInitLoading();
    }

    public interface Presenter{
        void getHabitacion();
        void getHabitacionBY(Habitacion habitacion);
    }

    public interface Model{
        interface OnLstHabitacionListener{
            void onInitLoading();
            void onFinished(ArrayList<Habitacion> lstHabitaciones);
            void onFailure(String error);
        }
        void getHabitacionService(HabitacionContract.Model.OnLstHabitacionListener habitacionListener);
        void getHabitacionServiceBY(HabitacionContract.Model.OnLstHabitacionListener habitacionListener, Habitacion habitacion);

    }
}
