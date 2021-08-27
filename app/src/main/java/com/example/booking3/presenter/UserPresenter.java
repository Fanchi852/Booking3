package com.example.booking3.presenter;


import com.example.booking3.beans.Cliente;
import com.example.booking3.interfaces.UserContract;
import com.example.booking3.model.UserModel;

import java.util.ArrayList;

public class UserPresenter implements UserContract.Presenter {

    private UserContract.View vista;
    private UserContract.Model model;

    public UserPresenter(UserContract.View vista) {
        this.vista = vista;
        this.model = new UserModel();
    }

    @Override
    public void login(Cliente cliente) {
        System.out.println("presenter");
        model.getUser(new UserContract.Model.UserListener(){


            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Cliente> cliente) {

                if(cliente == null || cliente.isEmpty()){
                    vista.failureLogin("el usuario o la contraseña con incorrectos");
                }else{
                    System.out.println("este es el cliente: "+cliente.toString());
                    vista.sucessLogin(cliente);
                }
            }

            @Override
            public void onFailure(String error) {

                vista.failureLogin(error);
            }
        }, cliente);
    }

    @Override
    public void register(Cliente cliente) {
        model.insertUser(new UserContract.Model.UserListener(){

            @Override
            public void onInitLoading() {
                vista.onInitLoading();
            }

            @Override
            public void onFinished(ArrayList<Cliente> cliente) {

                vista.sucessLogin(cliente);

            }

            @Override
            public void onFailure(String error) {
                vista.failureLogin("error al realizar la llamada");
            }
        }, cliente);
    }
}
