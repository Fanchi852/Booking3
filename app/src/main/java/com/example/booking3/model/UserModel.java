package com.example.booking3.model;

import com.example.booking3.beans.Cliente;
import com.example.booking3.interfaces.UserContract;
import com.example.booking3.retrofit.ApiAdapter;
import com.example.booking3.utils.Constantes;
import com.example.booking3.utils.TareaSegundoPlano;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserModel implements UserContract.Model{

    private UserListener userListener;
    //private ArrayList<Cliente> lstCliente;

    @Override
    public void getUser(final UserListener userListener, Cliente cliente) {
        System.out.println("userModel");
        System.out.println("userModel - creando adapter");
        ApiAdapter apiadapter = new ApiAdapter();
        System.out.println("userModel - creando request y asignando valor");
        Call<ArrayList<Cliente>> request = apiadapter.getCliente(cliente);
        System.out.println("userModel - llamando a calback");
        userListener.onInitLoading();
        request.enqueue(new Callback<ArrayList<Cliente>>() {
            @Override
            public void onResponse(Call<ArrayList<Cliente>> call, Response<ArrayList<Cliente>> response) {
                if (!response.body().isEmpty()) {
                    System.out.println("id del primer cliente: " + response.body());
                    userListener.onFinished(response.body());
                }else{
                    userListener.onFailure("error durante el login");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Cliente>> call, Throwable t) {
                System.out.println("userModel - fallo en la llamada");
                userListener.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void insertUser(UserListener userListener, Cliente cliente) {
        System.out.println("model");

        ApiAdapter apiclient = new ApiAdapter();
        Call<Object> request = apiclient.addClient(cliente);
        request.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println(response.body());
                Boolean res = (Boolean) response.body();
                if(res){
                    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
                    clientes.add(cliente);
                    userListener.onFinished(clientes);
                }else{
                    userListener.onFailure("Error");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                userListener.onFailure(t.getMessage());
            }
        });
    }
}