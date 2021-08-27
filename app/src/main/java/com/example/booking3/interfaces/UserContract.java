package com.example.booking3.interfaces;

import com.example.booking3.beans.Cliente;

import java.util.ArrayList;

public interface UserContract {

    public interface View{
        void sucessLogin (Boolean login);
        void failureLogin (String message );
        void onInitLoading();
    }

    public interface Presenter{
        void login(Cliente cliente);
        void register(Cliente cliente);
    }

    public interface Model{
        interface UserListener{
            void onInitLoading();
            void onFinished(ArrayList<Cliente> cliente);
            void onFailure(String error);
        }
        void getUser(UserListener userListener, Cliente cliente);
        void insertUser(UserListener userListener, Cliente cliente);
    }

}
