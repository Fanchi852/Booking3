package com.example.booking3.beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotel implements Comparable<Hotel> {

    private Integer id_hotel, estrellas, puntuacion, id_categoria, votos, habitaciones;
    private String direccion, nombre;

    private Categoria categoria;
    private List<Reserva> reservas;
    private List<Habitacion> listHabitaciones;
    private String compareMode;
    private DateFormat dateFormat;

    public Hotel(Integer id_hotel, Integer estrellas, Integer puntuacion, Integer id_categoria, Integer votos, Integer habitaciones, String direccion, String nombre) {
        this.id_hotel = id_hotel;
        this.estrellas = estrellas;
        this.puntuacion = puntuacion;
        this.id_categoria = id_categoria;
        this.votos = votos;
        this.habitaciones = habitaciones;
        this.direccion = direccion;
        this.categoria = new Categoria();
        this.nombre = nombre;
        this.reservas = new ArrayList<>();
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

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas, HashMap<Integer, List<ReservaHabitacion>> reservasHabitacionesMap) {
        this.reservas = reservas != null ? reservas : new ArrayList<>();
        for (Reserva reserva : this.reservas){
            reserva.setReservasHabitaciones(reservasHabitacionesMap.get(reserva.getId_reserva()));
        }
        System.out.println("Hotel: " + this.nombre + ", Reservas: " + this.reservas);
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
                ", categoria=" + categoria +
                ", reservas=" + reservas +
                '}';
    }

    public List<Habitacion> getHabitacionesDisponibles(Map<String, String> fechas) {
        if(listHabitaciones == null){
            listHabitaciones = new ArrayList<>();
            return listHabitaciones;
        }
        Map<Integer, Habitacion> habitacionesMap = new HashMap<>();
        if(!listHabitaciones.isEmpty()){
            for(Habitacion habitacion: listHabitaciones){
                habitacionesMap.put(habitacion.getId_habitacion(), habitacion);
            }
        }
        if(!habitacionesMap.keySet().isEmpty() && !reservas.isEmpty() && !fechas.isEmpty()){
            String fechaEntradaFiltroString = !fechas.get("fecha_entrada").isEmpty() ? fechas.get("fecha_entrada") : "";
            String fechaSalidaFiltroString = !fechas.get("fecha_salida").isEmpty() ? fechas.get("fecha_salida") : "";

            Date fechaEntradaFiltro = getDateFromString(fechaEntradaFiltroString);
            Date fechaSalidaFiltro = getDateFromString(fechaSalidaFiltroString);

            System.out.println("fechaEntradaFiltro: "+fechaEntradaFiltro);
            System.out.println("fechaSalidaFiltro; "+fechaSalidaFiltro);

            for(Reserva reserva:reservas){
                Date fechaEntradaReserva = getDateFromString(reserva.getFecha_entrada());
                Date fechaSalidaReserva = getDateFromString(reserva.getFecha_salida());

                System.out.println("fechaEntradaReserva: "+fechaEntradaReserva);
                System.out.println("fechaSalidaReserva; "+fechaSalidaReserva);

                if(fechaEntradaFiltro != null && fechaSalidaFiltro != null){
                    Boolean fechasMenores = fechaEntradaFiltro.before(fechaEntradaReserva) && fechaSalidaFiltro.before(fechaEntradaReserva);
                    Boolean fechasMayores = fechaEntradaFiltro.after(fechaSalidaReserva) && fechaSalidaFiltro.after(fechaSalidaReserva);
                    if(!fechasMenores && !fechasMayores){
                        System.out.println("Fecha no disponible en la reserva: " + reserva);
                        for(ReservaHabitacion reservaHabitacion: reserva.getReservasHabitaciones()){
                            habitacionesMap.remove(reservaHabitacion.getId_habitacion());
                        }
                    }
                }else if(fechaEntradaFiltro != null){
                    Boolean fueraDeRango = fechaEntradaFiltro.before(fechaEntradaReserva) || fechaEntradaFiltro.after(fechaSalidaReserva);
                    if(!fueraDeRango){
                        System.out.println("Fecha no disponible en la reserva: " + reserva);
                        for(ReservaHabitacion reservaHabitacion: reserva.getReservasHabitaciones()){
                            habitacionesMap.remove(reservaHabitacion.getId_habitacion());
                        }
                    }
                }else if(fechaSalidaFiltro != null){
                    Boolean fueraDeRango = fechaSalidaFiltro.before(fechaEntradaReserva) || fechaSalidaFiltro.after(fechaSalidaReserva);
                    if(!fueraDeRango){
                        System.out.println("Fecha no disponible en la reserva: " + reserva);
                        for(ReservaHabitacion reservaHabitacion: reserva.getReservasHabitaciones()){
                            habitacionesMap.remove(reservaHabitacion.getId_habitacion());
                        }
                    }
                }
            }
        }
        return new ArrayList<>(habitacionesMap.values());
    }

    public Double getPrecioHotel(String operacion){
        Double result = 0.0;
        Double precioTotal = 0.0;
        Double precioMaximo = 0.0;
        Double precioMinimo = 0.0;
        for(Habitacion habitacion: listHabitaciones){
            precioTotal += habitacion.getPrecio();
            precioMaximo = precioMaximo > habitacion.getPrecio() ? precioMaximo : habitacion.getPrecio();
            precioMinimo = precioMinimo < habitacion.getPrecio() ? precioMinimo : habitacion.getPrecio();
        }
        switch(operacion){
            case "Medio":
                result = precioTotal/listHabitaciones.size();
                break;
            case "Maximo":
                result = precioMaximo;
                break;
            case "Minimo":
                result = precioMinimo;
                break;
        }
        return result;
    }

    public void setCompareMode(String mode){
        this.compareMode = mode;
    }

    @Override
    public int compareTo(Hotel hotelInput) {
        Integer result = 0;
        switch(this.compareMode){
            case "Reservas":
                result = hotelInput.getReservas().size() - this.reservas.size();
                break;
            case "Destacado":
                result = hotelInput.getEstrellas() - this.estrellas;
                break;
            case "PrecioMasAlto":
                result = (int) (hotelInput.getPrecioHotel("Maximo") - getPrecioHotel("Maximo"));
                break;
            case "PreciosMasBbaratos":
                result = (int) (getPrecioHotel("Maximo") - hotelInput.getPrecioHotel("Maximo"));
                break;
            case "Puntuecion":
                result = hotelInput.getPuntuacion() - this.puntuacion;
                break;
        }
        return result;
    }

    public List<Habitacion> getListHabitaciones() {
        return listHabitaciones;
    }

    public void setListHabitaciones(List<Habitacion> listHabitaciones) {
        this.listHabitaciones = listHabitaciones;
    }

    public Date getDateFromString(String dateString){
        if(dateFormat==null) {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error de conversion de fecha: " + e.getMessage());
        }
        return date;
    }

}


