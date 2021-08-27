package com.example.booking3.presenter;

import com.example.booking3.beans.Habitacion;
import com.example.booking3.interfaces.HabitacionContract;
import com.example.booking3.model.HabitacionModel;

import java.util.ArrayList;

public class HabitacionPresenter implements HabitacionContract.Presenter {

    private HabitacionContract.View vista;
    private HabitacionContract.Model model;

    public HabitacionPresenter(HabitacionContract.View vista) {
        this.vista = vista;
        this.model = new HabitacionModel();
    }

    @Override
    public void getHabitacion() {
        model.getHabitacionService(new HabitacionContract.Model.OnLstHabitacionListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Habitacion> lstHabitaciones) {
                vista.sucessLstHabitacion(lstHabitaciones);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstHabitacion(error);
            }
        });
    }

    @Override
    public void getHabitacionBY(Habitacion habitacion) {
        model.getHabitacionServiceBY(new HabitacionContract.Model.OnLstHabitacionListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Habitacion> lstHabitaciones) {
                vista.sucessLstHabitacion(lstHabitaciones);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstHabitacion(error);
            }
        }, habitacion);
    }
}
