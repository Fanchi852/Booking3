package com.example.booking3.presenter;

import com.example.booking3.beans.Categoria;
import com.example.booking3.interfaces.CategoriaContract;
import com.example.booking3.interfaces.HotelContract;
import com.example.booking3.model.CategoriaModel;

import java.util.ArrayList;

public class CategoriaPresenter implements CategoriaContract.Presenter {

    private CategoriaContract.View vista;
    private CategoriaContract.Model model;

    public CategoriaPresenter(CategoriaContract.View vista) {
        this.vista = vista;
        this.model = new CategoriaModel();
    }

    @Override
    public void getCategorias() {
        model.getCategorias(new CategoriaContract.Model.CategoriaListener() {
            @Override
            public void onFinished(ArrayList<Categoria> lstCategoria) {
                vista.sucessGetCategorias(lstCategoria);
            }

            @Override
            public void onFailure(String error) {
                vista.failureGetCategorias(error);
            }
        });
    }
}
