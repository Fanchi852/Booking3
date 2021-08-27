package com.example.booking3.beans;

public class ReservaHabitacion {

    private Integer id_reservahabitacion, id_reserva, id_habitacion;

    public ReservaHabitacion(Integer id_reservahabitacion, Integer id_reserva, Integer id_habitacion) {
        this.id_reservahabitacion = id_reservahabitacion;
        this.id_reserva = id_reserva;
        this.id_habitacion = id_habitacion;
    }

    public ReservaHabitacion() {
    }

    public Integer getId_reservahabitacion() {
        return id_reservahabitacion;
    }

    public void setId_reservahabitacion(Integer id_reservahabitacion) {
        this.id_reservahabitacion = id_reservahabitacion;
    }

    public Integer getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Integer id_reserva) {
        this.id_reserva = id_reserva;
    }

    public Integer getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(Integer id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    @Override
    public String toString() {
        return "ReservaHabitacion{" +
                "id_reservahabitacion=" + id_reservahabitacion +
                ", id_reserva=" + id_reserva +
                ", id_habitacion=" + id_habitacion +
                '}';
    }
}
