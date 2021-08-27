package com.example.booking3.interfaces;

import com.example.booking3.beans.ReservaHabitacion;

import java.util.ArrayList;

public interface ReservaHabitacionContract {

    public interface View{
        void sucessLstReservasHabitaciones (ArrayList<ReservaHabitacion> lstReservasHabitaciones);
        void failureLstReservasHabitaciones (String message );
        void onInitLoading();
    }

    public interface Presenter{
        void getReservaHabitacion();
        void getReservaHabitacionBY(ReservaHabitacion reservaHabitacion);
    }

    public interface Model{
        interface OnLstReservasHabitacionesListener{
            void onInitLoading();
            void onFinished(ArrayList<ReservaHabitacion> lstReservasHabitaciones);
            void onFailure(String error);
        }
        void getReservaHabitacionService(ReservaHabitacionContract.Model.OnLstReservasHabitacionesListener reservaHabitacionListener);
        void getReservaHabitacionServiceBY(ReservaHabitacionContract.Model.OnLstReservasHabitacionesListener reservaHabitacionListener, ReservaHabitacion reservaHabitacion);

    }

}
