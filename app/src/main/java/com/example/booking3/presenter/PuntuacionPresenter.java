package com.example.booking3.presenter;

import com.example.booking3.beans.Puntuacion;
import com.example.booking3.interfaces.PuntuacionContract;

import java.util.ArrayList;

public class PuntuacionPresenter implements PuntuacionContract.Presenter {

    private PuntuacionContract.Model model;
    private PuntuacionContract.View vista;

    @Override
    public void getPuntuacion() {
        model.getPuntuacionService(new PuntuacionContract.Model.OnLstPuntuacionesListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Puntuacion> lstPuntuaciones) {
                vista.sucessLstPuntuaciones(lstPuntuaciones);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstPuntuaciones(error);
            }
        });
    }

    @Override
    public void getPuntuacionby(Puntuacion puntuacion) {
        model.getPuntuacionServiceBY(new PuntuacionContract.Model.OnLstPuntuacionesListener() {
            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Puntuacion> lstPuntuaciones) {
                vista.sucessLstPuntuaciones(lstPuntuaciones);
            }

            @Override
            public void onFailure(String error) {
                vista.failureLstPuntuaciones(error);
            }
        }, puntuacion);
    }
}
