package com.example.booking3.model;

import com.example.booking3.beans.Cliente;
import com.example.booking3.interfaces.UserContract;
import com.example.booking3.utils.Constantes;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class LoginModel implements UserContract.Model{

    @Override
    public void getUser(UserListener userListener, Cliente cliente) {
        String data = "Controller?ACTION=CLIENTE.FIND_BY&EMAIL = " + cliente.getEmail() + "&CONTRASENA = " + cliente.getContrasena();
        HttpURLConnection conx = null;
        String response = "";
        try{
            URL url = new URL(Constantes.SERVER_URL+":"+Constantes.SERVER_PORT+"/"+Constantes.SERVER_APP+"/" + data);
            conx = (HttpURLConnection)url.openConnection();
            conx.setRequestMethod("POST");
            conx.setRequestProperty("Content-Length",""+ Integer.toString(data.getBytes().length));
            conx.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(conx.getOutputStream());
            wr.writeBytes(data);
            wr.close();

            Scanner escan = new Scanner(conx.getInputStream());

            while(escan.hasNextLine()){

                response += (escan.nextLine());

            }
            if(response.length() > 1){
                //userListener.onFinished(clienteres);
            }else{
                //userListener.onFailure("no se encontraron resultados");
            }

        }catch(Exception e){
            System.out.println("Error en alguna parte de la conexion");
            System.out.println(e);
           // userListener.onFailure("error en la llamada");
        }
    }

    @Override
    public void insertUser(UserListener userListener, Cliente cliente) {

    }
}
