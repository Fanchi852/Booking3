package com.example.booking3.beans;

import java.sql.Date;

public class Reserva {

    private Integer id_cliente, id_hotel, habitaciones, id_reserva;
    private String fecha_entrada, fecha_salida;

    public Reserva(Integer id_cliente, Integer id_hotel, Integer habitaciones, Integer id_reserva, String fecha_entrada, String fecha_salida) {
        this.id_cliente = id_cliente;
        this.id_hotel = id_hotel;
        this.habitaciones = habitaciones;
        this.id_reserva = id_reserva;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
    }

    public Reserva() {
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

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Integer getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Integer id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getFecha_entrada() {
        return fecha_entrada;
    }

    public void setFecha_entrada(String fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id_cliente=" + id_cliente +
                ", id_hotel=" + id_hotel +
                ", habitaciones=" + habitaciones +
                ", id_reserva=" + id_reserva +
                ", fecha_entrada='" + fecha_entrada + '\'' +
                ", fecha_salida='" + fecha_salida + '\'' +
                '}';
    }
}
