package com.example.booking3.presenter;

import com.example.booking3.beans.ReservaHabitacion;
import com.example.booking3.interfaces.ReservaHabitacionContract;
import com.example.booking3.model.ReservaHabitacionModel;

import java.util.ArrayList;

public class ReservaHabitacionePresenter implements ReservaHabitacionContract.Presenter {

    private ReservaHabitacionContract.Model model;
    private ReservaHabitacionContract.View vista;

    public ReservaHabitacionePresenter(ReservaHabitacionContract.View vista) {
        this.vista = vista;
        this.model = new ReservaHabitacionModel();
    }

    @Override
    public void getReservaHabitacion() {
        model.getReservaHabitacionService(new ReservaHabitacionContract.Model.OnLstReservasHabitacionesListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<ReservaHabitacion> lstReservasHabitaciones) {
                vista.sucessLstReservasHabitaciones(lstReservasHabitaciones);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstReservasHabitaciones(error);
            }
        });
    }

    @Override
    public void getReservaHabitacionBY(ReservaHabitacion reservaHabitacion) {
        model.getReservaHabitacionServiceBY(new ReservaHabitacionContract.Model.OnLstReservasHabitacionesListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<ReservaHabitacion> lstReservasHabitaciones) {
                vista.sucessLstReservasHabitaciones(lstReservasHabitaciones);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstReservasHabitaciones(error);
            }
        }, reservaHabitacion);
    }
}
