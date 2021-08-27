package com.example.booking3.interfaces;

import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Puntuacion;

import java.util.ArrayList;

public interface PuntuacionContract {

    public interface View{
        void sucessLstPuntuaciones (ArrayList<Puntuacion> lstPuntuaciones);
        void failureLstPuntuaciones (String message );
        void onInitLoading();
    }

    public interface Presenter{
        void getPuntuacion();
        void getPuntuacionby(Puntuacion puntuacion);
    }

    public interface Model{
        interface OnLstPuntuacionesListener{
            void onInitLoading();
            void onFinished(ArrayList<Puntuacion> lstPuntuaciones);
            void onFailure(String error);
        }
        void getPuntuacionService(PuntuacionContract.Model.OnLstPuntuacionesListener puntuacionListener);
        void getPuntuacionServiceBY(PuntuacionContract.Model.OnLstPuntuacionesListener puntuacionListener, Puntuacion puntuacion);

    }

}
