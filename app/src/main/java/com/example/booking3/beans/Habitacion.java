package com.example.booking3.beans;

public class Habitacion {

    private Integer id_habitacion, precio, id_hotel;
    private String descripcion, nombre;

    public Habitacion(Integer id_habitacion, Integer precio, Integer id_hotel, String descripcion, String nombre) {
        this.id_habitacion = id_habitacion;
        this.precio = precio;
        this.id_hotel = id_hotel;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public Habitacion() {
    }

    public Integer getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(Integer id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(Integer id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "id_habitacion=" + id_habitacion +
                ", precio=" + precio +
                ", id_hotel=" + id_hotel +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
