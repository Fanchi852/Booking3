package com.example.booking3.beans;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Cliente {

    private String nombre, apellido, dni, contrasena, email, direccion;
    private Integer telefono, id_cliente;

    public Cliente(String nombre, String apellido, String dni, String contrasena, String email, String direccion, Integer telefono, Integer id_cliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.contrasena = contrasena;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.id_cliente = id_cliente;
    }

    public Cliente() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public static ArrayList<Cliente> getArrayListFromJSon(JSONArray datos){
        ArrayList<Cliente> lista = null;
        Cliente cliente = null;
        try {
            if(datos!=null && datos.length() > 0 ){
                lista = new ArrayList<Cliente>();
            }
            for (int i = 0; i < datos.length(); i++) {
                JSONObject json_data = datos.getJSONObject(i);
                cliente = new Cliente();
                cliente.setNombre(json_data.getString("nombre"));
                cliente.setApellido(json_data.getString("apellido"));
                cliente.setDni(json_data.getString("dni"));
                cliente.setEmail(json_data.getString("email"));
                cliente.setContrasena(json_data.getString("contrasena"));
                cliente.setDireccion(json_data.getString("direccion"));
                cliente.setTelefono(json_data.getInt("telefono"));
                cliente.setId_cliente(json_data.getInt("id_cliente"));
                lista.add(cliente);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public HashMap<String, String> toHashMap() {
        HashMap<String, String> res = new HashMap<>();
        if (nombre != null){
            res.put("NOMBRE",nombre);
        }
        if (apellido != null){
            res.put("APELLIDO",apellido);
        }
        if (dni != null) {
            res.put("DNI", dni);
        }
        if (contrasena != null){
            res.put("CONTRASENA",contrasena);
        }
        if (email != null){
            res.put("EMAIL",email);
        }
        if (direccion != null){
            res.put("DIRECCION",direccion);
        }
        if (telefono != null){
            res.put("TELEFONO",telefono.toString());
        }
        if (id_cliente != null) {
            res.put("ID_CLIENTE", id_cliente.toString());
        }
        return res;
    }

    public Cliente fromHashMap(HashMap<String, String> map){
        Cliente cliente = new Cliente();

        cliente.setNombre(map.get("NOMBRE"));
        cliente.setApellido(map.get("APELLIDO"));
        cliente.setDni(map.get("DNI"));
        cliente.setDireccion(map.get("DIRECCION"));
        cliente.setEmail(map.get("EMAIL"));
        cliente.setContrasena(map.get("CONTRASENA"));
        cliente.setTelefono(Integer.parseInt(map.get("TELEFONO")));
        cliente.setId_cliente(Integer.parseInt(map.get("ID_CLIENTE")));

        return cliente;
    }



    public String toString(){
        String res = "";

        res += "nombre : " + this.nombre + ", apellido : " + this.apellido + ", dni : " + this.dni + ", direccion : " + this.direccion +
                ", email : " + this.email + ", contrasena : " + this.contrasena + ", telefono : " + this.telefono + ", id_cliente : " + this.id_cliente;

        return res;
    }

}
