package com.example.booking3.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.booking3.beans.Cliente;
import com.example.booking3.interfaces.UserContract;

import com.example.booking3.interfaces.UserContract.Model.UserListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

    // HILO ASYNCTASK
    public class TareaSegundoPlano extends AsyncTask<String, Integer, Boolean> {
        private ArrayList<Cliente> LstCliente;
        private Cliente cliente = new Cliente();
        public HashMap<String, String> parametros = null;
        private UserListener userListener;

        public ArrayList<Cliente> getLstCliente() {
            return LstCliente;
        }

        //Comentado: private ArrayList<Cliente> listaClientes = null;
        public TareaSegundoPlano(final UserListener userListener, HashMap<String, String> parametros) {
            super();
            this.parametros = parametros;
            this.userListener = userListener;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            System.out.println("segundo plano");
            String url_select = params[0];
            try {
                Post post = new Post();
                JSONArray result = post.getServerDataPost(parametros,url_select);
                //System.out.println(result);
//Comentado: listaClientes =
                LstCliente = cliente.getArrayListFromJSon(result);
            } catch (Exception e) {
                Log.e("log_tag_Asynctask_1", "Error in http connection " + e.toString());
//messageUser = "Error al conectar con el servidor. ";
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean resp) {
            try {
                if(resp){
                    this.userListener.onFinished(LstCliente);
                }
                else{
                    this.userListener.onFailure("usuario no encontrado en la base de datos");
                }
            }catch (Exception e) {
                this.userListener.onFailure(e.toString());
                Log.e("log_tag_Asynctask_2", "Error in http connection " + e.toString());
            }
        }
    }
