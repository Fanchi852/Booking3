package com.example.booking3.model;

import com.example.booking3.beans.Categoria;
import com.example.booking3.interfaces.CategoriaContract;
import com.example.booking3.retrofit.ApiAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaModel implements CategoriaContract.Model {


    @Override
    public void getCategorias(CategoriaListener categoriaListener) {

        ApiAdapter apiclient = new ApiAdapter();
        Call<ArrayList<Categoria>> request = apiclient.getCategorias();

        request.enqueue(new Callback<ArrayList<Categoria>>() {
            @Override
            public void onResponse(Call<ArrayList<Categoria>> call, Response<ArrayList<Categoria>> response) {
                System.out.println("esta es la lista de categorias" + response.body().toString());
                categoriaListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Categoria>> call, Throwable t) {
                categoriaListener.onFailure(t.getMessage());
            }
        });
    }
}
