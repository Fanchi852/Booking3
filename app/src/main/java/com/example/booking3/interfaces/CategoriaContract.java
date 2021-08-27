package com.example.booking3.interfaces;

import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Cliente;
import com.example.booking3.beans.Hotel;

import java.util.ArrayList;

public interface CategoriaContract {

    public interface View{
        void sucessGetCategorias (ArrayList<Categoria> lstCategoria);
        void failureGetCategorias (String message );
    }

    public interface Presenter{
        void getCategorias();
    }

    public interface Model{
        interface CategoriaListener{
            void onFinished(ArrayList<Categoria> lstCategoria);
            void onFailure(String error);
        }
        void getCategorias(CategoriaContract.Model.CategoriaListener categoriaListener);
    }

}
