package com.example.booking3.beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Hotel {

    private Integer id_hotel, estrellas, puntuacion, id_categoria, votos, habitaciones, habitaciones_libres;
    private String direccion, nombre;

    private Categoria categoria;

    public Hotel(Integer id_hotel, Integer estrellas, Integer puntuacion, Integer id_categoria, Integer votos, Integer habitaciones, String direccion, String nombre) {
        this.id_hotel = id_hotel;
        this.estrellas = estrellas;
        this.puntuacion = puntuacion;
        this.id_categoria = id_categoria;
        this.votos = votos;
        this.habitaciones = habitaciones;
        this.direccion = direccion;
        this.nombre = nombre;

    }

    public Hotel() {
    }

    public Integer getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Integer getVotos() {
        return votos;
    }

    public void setVotos(Integer votos) {
        this.votos = votos;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public static ArrayList<Hotel> getArrayListFromJSon(JSONArray datos){
        ArrayList<Hotel> lista = null;
        Hotel hotel = null;
        try {
            if(datos!=null && datos.length() > 0 ){
                lista = new ArrayList<Hotel>();
            }
            for (int i = 0; i < datos.length(); i++) {
                JSONObject json_data = datos.getJSONObject(i);
                hotel = new Hotel();
                hotel.setNombre(json_data.getString("nombre"));
                hotel.setEstrellas(json_data.getInt("estrellas"));
                hotel.setHabitaciones(json_data.getInt("habitaciones"));
                hotel.setPuntuacion(json_data.getInt("puntuacion"));
                hotel.setVotos(json_data.getInt("votos"));
                hotel.setDireccion(json_data.getString("direccion"));
                hotel.setId_hotel(json_data.getInt("id_hotel"));
                hotel.setId_categoria(json_data.getInt("id_categoria"));
                lista.add(hotel);
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
        if (estrellas != null){
            res.put("ESTRELLAS",estrellas.toString());
        }
        if (puntuacion != null) {
            res.put("PUNTUACION", puntuacion.toString());
        }
        if (id_categoria != null){
            res.put("ID_CATEGORIA", id_categoria.toString());
        }
        if (votos != null){
            res.put("VOTOS", votos.toString());
        }
        if (direccion != null){
            res.put("DIRECCION", direccion);
        }
        if (habitaciones != null){
            res.put("HABITACIONES", habitaciones.toString());
        }
        if (id_hotel != null) {
            res.put("ID_HOTEL", id_hotel.toString());
        }
        return res;
    }

    public Hotel fromHashMap(HashMap<String, String> map){
        Hotel hotel = new Hotel();

        hotel.setNombre(map.get("NOMBRE"));
        hotel.setVotos(Integer.parseInt(map.get("VOTOS")));
        hotel.setId_categoria(Integer.parseInt(map.get("ID_CATEGORIA")));
        hotel.setDireccion(map.get("DIRECCION"));
        hotel.setPuntuacion(Integer.parseInt(map.get("PUNTUACION")));
        hotel.setHabitaciones(Integer.parseInt(map.get("HABITACIONES")));
        hotel.setEstrellas(Integer.parseInt(map.get("ESTRELLAS")));
        hotel.setId_hotel(Integer.parseInt(map.get("ID_HOTEL")));

        return hotel;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id_hotel=" + id_hotel +
                ", estrellas=" + estrellas +
                ", puntuacion=" + puntuacion +
                ", id_categoria=" + id_categoria +
                ", votos=" + votos +
                ", habitaciones=" + habitaciones +
                ", direccion='" + direccion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + ((categoria != null) ? categoria.toString() : "") + '\'' +
                '}';
    }
}


