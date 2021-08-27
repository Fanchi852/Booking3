package com.example.booking3.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.booking3.R;
import com.example.booking3.adapter.HotelAdapter;
import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Habitacion;
import com.example.booking3.beans.Hotel;
import com.example.booking3.beans.Reserva;
import com.example.booking3.beans.ReservaHabitacion;
import com.example.booking3.interfaces.CategoriaContract;
import com.example.booking3.interfaces.HotelContract;
import com.example.booking3.interfaces.ReservaContract;
import com.example.booking3.interfaces.ReservaHabitacionContract;
import com.example.booking3.presenter.CategoriaPresenter;
import com.example.booking3.presenter.HotelPresenter;
import com.example.booking3.presenter.ReservaHabitacionePresenter;
import com.example.booking3.presenter.ReservaPresenter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HotelListActivity extends AppCompatActivity implements HotelContract.View, CategoriaContract.View, ReservaContract.View, ReservaHabitacionContract.View {

    private RecyclerView recyclre;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Button btfiltro;
    private EditText fechaEntradaEdit, fechaSalidaEdit, numeroDePersonas, DireccionEdit;
    private Spinner filtrospinner;

    private HotelPresenter hotelPresenter;
    private CategoriaPresenter categoriaPresenter;
    private ReservaPresenter reservaPresenter;
    private ReservaHabitacionePresenter reservaHabitacionePresenter;
    private HashMap<Integer, Categoria> categoriasMap;
    private HashMap<Integer, Hotel> hotelesMap;
    private HashMap<Integer, List<Reserva>> reservasMap;
    private HashMap<Integer, List<ReservaHabitacion>> reservasHabitacionesMap;
    private HashMap<String, Boolean> loadStates;
    private HashMap<String, String> hotelFiltro;
    private DateFormat dateFormat;


    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        btfiltro = findViewById(R.id.btfiltro);
        filtrospinner = findViewById(R.id.filtrospinner);
        DireccionEdit = findViewById(R.id.DireccionEdit);
        numeroDePersonas = findViewById(R.id.numeroDePersonas);
        fechaSalidaEdit = findViewById(R.id.fechaSalidaEdit);
        fechaEntradaEdit = findViewById(R.id.fechaEntradaEdit);
        progressBar = findViewById(R.id.progressBar);
        recyclre = (RecyclerView) findViewById(R.id.reciclador);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filtrospinnerdata, android.R.layout.simple_spinner_item);
        filtrospinner.setAdapter(adapter);

        categoriaPresenter = new CategoriaPresenter(this);
        reservaPresenter = new ReservaPresenter(this);
        hotelPresenter = new HotelPresenter(this);
        reservaHabitacionePresenter = new ReservaHabitacionePresenter(this);
        categoriasMap = new HashMap<>();
        loadStates = new HashMap<>();
        hotelesMap = new HashMap<>();
        hotelFiltro = new HashMap<>();
        reservasMap = new HashMap<>();
        reservasHabitacionesMap = new HashMap<>();
        lManager = new LinearLayoutManager(this);

        recyclre.setLayoutManager(lManager);
        recyclre.setHasFixedSize(true);

        buscarHoteles();


        /*---- BOTON DE FILTRADO ----*/

        btfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hotelFiltro.clear();

                hotelFiltro.put("fecha_entrada", fechaEntradaEdit.getText().toString());
                hotelFiltro.put("fecha_salida", fechaSalidaEdit.getText().toString());
                hotelFiltro.put("numero_personas", numeroDePersonas.getText().toString());
                hotelFiltro.put("direccion", DireccionEdit.getText().toString());
                hotelFiltro.put("filtro_spinner", filtrospinner.getItemAtPosition(filtrospinner.getSelectedItemPosition()).toString());

                buscarHoteles();
            }
        });

    }

    @Override
    public void sucessLstHotel(ArrayList<Hotel> lstHoteles) {
        System.out.println("terminada la peticion de hoteles: "+lstHoteles.toString());
        for (Hotel hotel : lstHoteles) {
            hotelesMap.put(hotel.getId_hotel(), hotel);

        }
        loadStates.put("hoteles", true);
        parseHoteles();
    }

    @Override
    public void failureLstHotel(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("-- ERROR --");
        AlertDialog dialog = builder.create();
        dialog.show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void sucessLstReservas(ArrayList<Reserva> lstReservas) {

        System.out.println("terminada la peticion de Reservas: "+lstReservas.toString());
        for (Reserva reserva : lstReservas) {
            if (!reservasMap.containsKey(reserva.getId_hotel())){
                reservasMap.put(reserva.getId_hotel(), new ArrayList<>());
            }
            reservasMap.get(reserva.getId_hotel()).add(reserva);
        }

        loadStates.put("reservas", true);
        parseHoteles();

    }

    @Override
    public void failureLstReservas(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("-- ERROR --");
        AlertDialog dialog = builder.create();
        dialog.show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void sucessLstReservasHabitaciones(ArrayList<ReservaHabitacion> lstReservasHabitaciones) {
        System.out.println("terminada la peticion de Reservas: "+lstReservasHabitaciones.toString());
        for (ReservaHabitacion reservaHabitacion : lstReservasHabitaciones) {
            if (!reservasHabitacionesMap.containsKey(reservaHabitacion.getId_reserva())){
                reservasHabitacionesMap.put(reservaHabitacion.getId_reserva(), new ArrayList<>());
            }
            reservasHabitacionesMap.get(reservaHabitacion.getId_reserva()).add(reservaHabitacion);
        }

        loadStates.put("reservashabitacion", true);
        parseHoteles();
    }

    @Override
    public void failureLstReservasHabitaciones(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("-- ERROR --");
        AlertDialog dialog = builder.create();
        dialog.show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onInitLoading() {
        System.out.println("--------------------- LOADING------------------");
        progressBar.setMax(10);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        recyclre.setVisibility(View.GONE);
    }

    @Override
    public void sucessGetCategorias(ArrayList<Categoria> lstCategoria) {
        System.out.println("terminada la peticion de categorias: "+lstCategoria.toString());
        for (Categoria categoria : lstCategoria) {
            categoriasMap.put(categoria.getId_categoria(), categoria);
        }
        loadStates.put("categorias", true);
        parseHoteles();
    }

    @Override
    public void failureGetCategorias(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("-- ERROR --");
        AlertDialog dialog = builder.create();
        dialog.show();
        progressBar.setVisibility(View.GONE);
    }

    public void buscarHoteles(){

        reservasMap.clear();
        categoriasMap.clear();
        hotelesMap.clear();
        reservasHabitacionesMap.clear();


        loadStates.put("hoteles", false);
        loadStates.put("categorias", false);
        loadStates.put("reservas", false);
        loadStates.put("reservashabitacion", false);
        reservaHabitacionePresenter.getReservaHabitacion();
        hotelPresenter.getHotel();
        categoriaPresenter.getCategorias();
        reservaPresenter.getReserva();

    }

    public void parseHoteles() {
        if(loadStates.containsValue(false)){
            System.out.println("parsehotel - loadState fail; " + loadStates.toString());
            return;
        }
        System.out.println("parsehotel - tras el if");
        ArrayList<Hotel> lstHoteles = new ArrayList<>();
        for (Hotel hotel : hotelesMap.values()) {
            System.out.println("parsehotel - en el for");
            hotel.setCategoria(categoriasMap.get(hotel.getId_categoria()));

            if(cumpleFiltro(hotel)){
                lstHoteles.add(hotel);
            }

        }
        System.out.println("parsehotel - tras el for");
        adapter = new HotelAdapter(lstHoteles,this);
        recyclre.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclre.setVisibility(View.VISIBLE);
    }

    /*hotelFiltro.put("fecha_entrada", fechaEntradaEdit.getText().toString());
    hotelFiltro.put("fecha_salida", fechaSalidaEdit.getText().toString());
    hotelFiltro.put("numero_personas", numeroDePersonas.getText().toString());
    hotelFiltro.put("direccion", DireccionEdit.getText().toString());
    hotelFiltro.put("filtro_spinner", filtrospinner.getItemAtPosition(filtrospinner.getSelectedItemPosition()).toString());*/
    private boolean cumpleFiltro(Hotel hotel) {
        if (hotelFiltro == null || hotelFiltro.isEmpty()){

            return true;
        }
        Integer habitacionesDisponibles = hotel.getHabitaciones();
        List<Reserva> listaReservas = reservasMap.get(hotel.getId_hotel());
        Boolean result = true;
        System.out.println(hotel.toString());

        //filtro direccion

        if (!hotelFiltro.get("direccion").isEmpty()) {

            String direccionfiltro = hotelFiltro.get("direccion");
            String direccionHotel = hotel.getDireccion();
            System.out.println("direccionHotel; " + direccionHotel);
            System.out.println("direccionfiltro; " + direccionfiltro);
            if (!direccionHotel.contains(direccionfiltro)) {
                return false;
            }
        }

        //filtro fecha entrada
        if (!hotelFiltro.get("fecha_entrada").isEmpty() && listaReservas != null){
            System.out.println(listaReservas.toString());
            Date fechaEntradaFiltro = getDateFromString(hotelFiltro.get("fecha_entrada"));
            for(Reserva reserva:listaReservas){
                Date fechaEntradaReserva = getDateFromString(reserva.getFecha_entrada());
                Date fechaSalidaReserva = getDateFromString(reserva.getFecha_salida());
                System.out.println("fechaEntradaFiltro: "+fechaEntradaFiltro);
                System.out.println("fechaEntradaReserva; "+fechaEntradaReserva);
                System.out.println("fechaSalidaReserva: "+fechaSalidaReserva);
                if(!(fechaEntradaFiltro.before(fechaEntradaReserva) || fechaEntradaFiltro.after(fechaSalidaReserva))){
                    System.out.println("fecha no disponible para la reserva: " + reserva.getId_reserva());
                    System.out.println("habitaciones no disponible: " + reservasHabitacionesMap.get(reserva.getId_reserva()));
                    habitacionesDisponibles -= reservasHabitacionesMap.get(reserva.getId_reserva()).size();
                }else{
                    System.out.println("fecha disponible: " + reserva.getId_reserva());
                }
            }
        }
        //filtro fecha salida
        if (!hotelFiltro.get("fecha_salida").isEmpty() && listaReservas != null){
            System.out.println(listaReservas.toString());
            Date fechaSalidaFiltro = getDateFromString(hotelFiltro.get("fecha_salida"));
            for(Reserva reserva:listaReservas){
                Date fechaSalidaReserva = getDateFromString(reserva.getFecha_entrada());
                Date fechaEntradaReserva = getDateFromString(reserva.getFecha_salida());
                System.out.println("fechaEntradaFiltro: "+fechaSalidaFiltro);
                System.out.println("fechaEntradaReserva; "+fechaSalidaReserva);
                System.out.println("fechaSalidaReserva: "+fechaEntradaReserva);
                if(!(fechaSalidaFiltro.before(fechaSalidaReserva) || fechaSalidaFiltro.after(fechaEntradaReserva))){
                    System.out.println("fecha no disponible: " + reserva.getId_reserva());
                    System.out.println("habitaciones no disponible: " + reservasHabitacionesMap.get(reserva.getId_reserva()));
                    habitacionesDisponibles -= reservasHabitacionesMap.get(reserva.getId_reserva()).size();
                }else{
                    System.out.println("fecha disponible: " + reserva.getId_reserva());
                }
            }
        }




        //filtro numero de personas

        if (!hotelFiltro.get("numero_personas").isEmpty()){

            String personasfiltro = hotelFiltro.get("numero_personas");
            Integer numPersonasFiltro = Integer.valueOf(hotelFiltro.get("numero_personas"));
            System.out.println("numPersonasFiltro; " + numPersonasFiltro);
            System.out.println("habitacionesDisponibles; " + habitacionesDisponibles);
            System.out.println("personasfiltro; " + personasfiltro);
            if(numPersonasFiltro>habitacionesDisponibles){
                result=false;
            }
        }else{
            if(habitacionesDisponibles<=0){
                result=false;
            }
        }

        System.out.println("habitaciones disponible: " + habitacionesDisponibles);
        return result;
    }

    public Date getDateFromString(String dateString){
        if(dateFormat==null) {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getDateFromSqlDate(java.sql.Date sqlDate){
        Date date = null;
        date = new Date(sqlDate.getTime());
        return new java.sql.Date(date.getTime());
    }
}