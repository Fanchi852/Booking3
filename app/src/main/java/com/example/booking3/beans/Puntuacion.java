package com.example.booking3.beans;

public class Puntuacion {

    private Integer id_puntuacion, id_cliente, id_hotel, puntuacion;

    public Puntuacion(Integer id_puntuacion, Integer id_cliente, Integer id_hotel, Integer puntuacion) {
        this.id_puntuacion = id_puntuacion;
        this.id_cliente = id_cliente;
        this.id_hotel = id_hotel;
        this.puntuacion = puntuacion;
    }

    public Puntuacion() {
    }

    public Integer getId_puntuacion() {
        return id_puntuacion;
    }

    public void setId_puntuacion(Integer id_puntuacion) {
        this.id_puntuacion = id_puntuacion;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

}
