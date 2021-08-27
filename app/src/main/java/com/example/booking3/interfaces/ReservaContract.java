package com.example.booking3.interfaces;

import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Reserva;

import java.util.ArrayList;

public interface ReservaContract {

    public interface View{
        void sucessLstReservas (ArrayList<Reserva> lstReservas);
        void failureLstReservas (String message );
        void onInitLoading();
    }

    public interface Presenter{
        void getReserva();
        void getReservaBY(Reserva reserva);
        void addReserva(Reserva reserva);
    }

    public interface Model{
        interface OnLstReservasListener{
            void onInitLoading();
            void onFinished(ArrayList<Reserva> lstReservas);
            void onFailure(String error);
        }
        void getReservaService(ReservaContract.Model.OnLstReservasListener reservaListener);
        void getReservaServiceBY(ReservaContract.Model.OnLstReservasListener reservaListener, Reserva reserva);
        void addReservaService(ReservaContract.Model.OnLstReservasListener reservaListener, Reserva reserva);

    }

}
