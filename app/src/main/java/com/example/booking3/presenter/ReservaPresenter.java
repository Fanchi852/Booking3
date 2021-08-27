package com.example.booking3.presenter;

import com.example.booking3.beans.Reserva;
import com.example.booking3.interfaces.ReservaContract;
import com.example.booking3.model.ReservaModel;

import java.util.ArrayList;

public class ReservaPresenter implements ReservaContract.Presenter {

    private ReservaContract.Model model;
    private ReservaContract.View vista;

    public ReservaPresenter(ReservaContract.View vista) {
        this.model = new ReservaModel();
        this.vista = vista;
    }

    @Override
    public void getReserva() {
        model.getReservaService(new ReservaContract.Model.OnLstReservasListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Reserva> lstReservas) {
                vista.sucessLstReservas(lstReservas);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstReservas(error);
            }
        });
    }
}
