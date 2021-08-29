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
import com.example.booking3.interfaces.HabitacionContract;
import com.example.booking3.interfaces.HotelContract;
import com.example.booking3.interfaces.ReservaContract;
import com.example.booking3.interfaces.ReservaHabitacionContract;
import com.example.booking3.presenter.CategoriaPresenter;
import com.example.booking3.presenter.HabitacionPresenter;
import com.example.booking3.presenter.HotelPresenter;
import com.example.booking3.presenter.ReservaHabitacionePresenter;
import com.example.booking3.presenter.ReservaPresenter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelListActivity extends AppCompatActivity implements HotelContract.View, CategoriaContract.View, ReservaContract.View, ReservaHabitacionContract.View, HabitacionContract.View {

    private RecyclerView recyclre;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Button btfiltro;
    private EditText fechaEntradaEdit, fechaSalidaEdit, numeroDePersonas, DireccionEdit;
    private Spinner filtrospinner;

    private HotelPresenter hotelPresenter;
    private CategoriaPresenter categoriaPresenter;
    private ReservaPresenter reservaPresenter;
    private HabitacionPresenter habitacionPresenter;
    private ReservaHabitacionePresenter reservaHabitacionePresenter;

    private HashMap<Integer, Categoria> categoriasMap;
    private HashMap<Integer, Hotel> hotelesMap;
    private HashMap<Integer, List<Habitacion>> habitacionesMap;
    private HashMap<Integer, List<Reserva>> reservasMap;
    private HashMap<Integer, List<ReservaHabitacion>> reservasHabitacionesMap;
    private HashMap<String, Boolean> loadStates;
    private HashMap<String, String> hotelFiltro;
    private DateFormat dateFormat;

    private Bundle clienteData;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        clienteData = getIntent().getExtras();

        btfiltro = findViewById(R.id.btreserva);
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
        habitacionPresenter = new HabitacionPresenter(this);
        reservaHabitacionePresenter = new ReservaHabitacionePresenter(this);

        categoriasMap = new HashMap<>();
        loadStates = new HashMap<>();
        hotelesMap = new HashMap<>();
        hotelFiltro = new HashMap<>();
        habitacionesMap = new HashMap<>();
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

                String fechaEntradaFiltroString = !fechaEntradaEdit.getText().toString().isEmpty() ? fechaEntradaEdit.getText().toString() : "";
                String fechaSalidaFiltroString = !fechaSalidaEdit.getText().toString().isEmpty() ? fechaSalidaEdit.getText().toString() : "";

                Date fechaEntradaFiltro = getDateFromString(fechaEntradaFiltroString);
                Date fechaSalidaFiltro = getDateFromString(fechaSalidaFiltroString);

                if(!fechaEntradaFiltroString.isEmpty() && fechaEntradaFiltro == null){
                    showMessageOnFinish("Error", "Formato de fecha de entrada incorrecto, utilice DD/MM/AAAA");
                    return;
                }

                if(!fechaSalidaFiltroString.isEmpty() && fechaSalidaFiltro == null){
                    showMessageOnFinish("Error", "Formato de fecha de salida incorrecto, utilice DD/MM/AAAA");
                    return;
                }

                if(fechaEntradaFiltro != null && fechaSalidaFiltro != null){
                    if(fechaEntradaFiltro.after(fechaEntradaFiltro)){
                        showMessageOnFinish("Error", "Rango de fechas no válido");
                        return;
                    }
                }

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
        for (Hotel hotel : lstHoteles) {
            hotelesMap.put(hotel.getId_hotel(), hotel);
        }
        loadStates.put("hoteles", true);
        parseHoteles();
    }

    @Override
    public void failureLstHotel(String message) {
        showMessageOnFinish("Error", "no se pudieron cargar los hoteles");
    }

    @Override
    public void sucessLstReservas(ArrayList<Reserva> lstReservas) {
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
        showMessageOnFinish("Error", "no se pudieron cargar las reservas");
    }

    @Override
    public void sucessLstReservasHabitaciones(ArrayList<ReservaHabitacion> lstReservasHabitaciones) {
        for (ReservaHabitacion reservaHabitacion : lstReservasHabitaciones) {
            if (!reservasHabitacionesMap.containsKey(reservaHabitacion.getId_reserva())){
                reservasHabitacionesMap.put(reservaHabitacion.getId_reserva(), new ArrayList<>());
            }
            reservasHabitacionesMap.get(reservaHabitacion.getId_reserva()).add(reservaHabitacion);
        }
        System.out.println("reservasHabitacionesMap: " + reservasHabitacionesMap);
        loadStates.put("reservashabitacion", true);
        parseHoteles();
    }

    @Override
    public void failureLstReservasHabitaciones(String message) {
        showMessageOnFinish("Error", "no se pudieron cargar las habitaciones reservadas");
    }

    @Override
    public void sucessLstHabitacion(ArrayList<Habitacion> lstHabitaciones) {
        for (Habitacion habitacion : lstHabitaciones) {
            if (!habitacionesMap.containsKey(habitacion.getId_hotel())){
                habitacionesMap.put(habitacion.getId_hotel(), new ArrayList<>());
            }
            habitacionesMap.get(habitacion.getId_hotel()).add(habitacion);
        }
        loadStates.put("habitaciones", true);
        parseHoteles();
    }

    @Override
    public void failureLstHabitacion(String message) {
        showMessageOnFinish("Error", "no se pudieron cargar las habitaciones");
    }

    @Override
    public void onInitLoading() {
        System.out.println("--------------------- LOADING------------------");
        progressBar.setMax(10);
        progressBar.setProgress(0);

        /* GESTION DE CARGA */
        progressBar.setVisibility(View.VISIBLE);
        recyclre.setVisibility(View.GONE);
    }

    @Override
    public void sucessGetCategorias(ArrayList<Categoria> lstCategoria) {
        for (Categoria categoria : lstCategoria) {
            categoriasMap.put(categoria.getId_categoria(), categoria);
        }
        loadStates.put("categorias", true);
        parseHoteles();
    }

    @Override
    public void failureGetCategorias(String message) {
        showMessageOnFinish("Error", "no se pudieron cargar las categorías");
    }

    public void buscarHoteles(){

        reservasMap.clear();
        categoriasMap.clear();
        hotelesMap.clear();
        reservasHabitacionesMap.clear();
        habitacionesMap.clear();

        loadStates.put("hoteles", false);
        loadStates.put("categorias", false);
        loadStates.put("reservas", false);
        loadStates.put("reservashabitacion", false);
        loadStates.put("habitaciones", false);

        reservaHabitacionePresenter.getReservaHabitacion();
        hotelPresenter.getHotel();
        categoriaPresenter.getCategorias();
        reservaPresenter.getReserva();
        habitacionPresenter.getHabitacion();

    }

    public void parseHoteles() {
        System.out.println("loadStates: " + loadStates.toString());
        if(loadStates.containsValue(false)){
            System.out.println("Faltan elementos por cargar");
            return;
        }
        System.out.println("Todos los elementos se han cargado");

        List<Hotel> lstHoteles = new ArrayList<>();
        for (Hotel hotel : hotelesMap.values()) {
            hotel.setCategoria(categoriasMap.get(hotel.getId_categoria()));
            hotel.setReservas(reservasMap.get(hotel.getId_hotel()), reservasHabitacionesMap);
            hotel.setListHabitaciones(habitacionesMap.get(hotel.getId_hotel()));

            if(cumpleFiltro(hotel)){
                lstHoteles.add(hotel);
            }
        }

        if(!lstHoteles.isEmpty() && hotelFiltro != null && !hotelFiltro.isEmpty() && !hotelFiltro.get("filtro_spinner").isEmpty()){
            lstHoteles = aplicarFiltroSpinner(lstHoteles);
        }

        System.out.println("Lista de hoteles filtrada: " + lstHoteles);
        for(Hotel hotel: lstHoteles){
            System.out.println(
                    "Hotel: " + hotel.getNombre() +
                    ", Reservas: " + hotel.getReservas().size() +
                    ", Estrellas: " + hotel.getEstrellas() +
                    ", Puntuacion: " + hotel.getPuntuacion() +
                    ", Precio: " + hotel.getPrecioHotel("Maximo")
            );
        }

        adapter = new HotelAdapter(lstHoteles, clienteData, this);
        recyclre.setAdapter(adapter);

        showMessageOnFinish("Success", "Carga completada");
    }

    private List<Hotel> aplicarFiltroSpinner(List<Hotel> lstHoteles) {

        System.out.println("Filtro spinner seleccionado: " + hotelFiltro.get("filtro_spinner"));

        List<Hotel> result;
        Integer numResults = null;

        for(Hotel hotel: lstHoteles){
            switch(hotelFiltro.get("filtro_spinner")){
                case "10 hoteles con mas reservas":
                    hotel.setCompareMode("Reservas");
                    numResults = 10;
                    break;
                case "Destacados":
                    hotel.setCompareMode("Destacado");
                    numResults = 10;
                    break;
                case "Precios mas caros":
                    hotel.setCompareMode("PrecioMasAlto");
                    numResults = 10;
                    break;
                case "Precios mas baratos":
                    hotel.setCompareMode("PreciosMasBbaratos");
                    numResults = 10;
                    break;
                case "Puntuacion de usuarios":
                    hotel.setCompareMode("Puntuecion");
                    numResults = 10;
                    break;
                default:
                    return lstHoteles;
            }
        }

        Collections.sort(lstHoteles);

        if(numResults != null){
            numResults = numResults > lstHoteles.size() ? lstHoteles.size() : numResults;
            result = lstHoteles.subList(0, numResults);
        }else{
            result = lstHoteles;
        }

        return result;

    }

    private boolean cumpleFiltro(Hotel hotel) {
        if (hotelFiltro == null || hotelFiltro.isEmpty()){
            return true;
        }

        Boolean result = true;

        System.out.println(hotel.toString());

        /* FILTRO DIRECCION */
        if (!hotelFiltro.get("direccion").isEmpty()) {
            String direccionfiltro = hotelFiltro.get("direccion");
            String direccionHotel = hotel.getDireccion();

            System.out.println("direccionHotel; " + direccionHotel);
            System.out.println("direccionfiltro; " + direccionfiltro);

            if (!direccionHotel.contains(direccionfiltro)) {
                return false;
            }
        }

        /* FILTRO FECHAS */
        List<Habitacion> habitacionesHotel = hotel.getHabitacionesDisponibles(hotelFiltro);
        Integer habitacionesDisponibles = habitacionesHotel.size();

        /* FILTRO NUMERO DE PERSONAS */
        if (!hotelFiltro.get("numero_personas").isEmpty()){
            Integer numPersonasFiltro = Integer.valueOf(hotelFiltro.get("numero_personas"));

            System.out.println("numPersonasFiltro; " + numPersonasFiltro);
            System.out.println("habitacionesDisponibles; " + habitacionesDisponibles);

            if(numPersonasFiltro > habitacionesDisponibles){
                result=false;
            }
        }else{
            if(habitacionesDisponibles <= 0){
                result=false;
            }
        }

        System.out.println("Hotel disponible: " + result);
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
            System.out.println("Error de conversion de fecha: " + e.getMessage());
        }
        return date;
    }

    private void showMessageOnFinish(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();

        /* GESTION DE CARGA */
        switch(title){
            case "Success":
                recyclre.setVisibility(View.VISIBLE);
            case "Error":
                progressBar.setVisibility(View.GONE);
        }
    }
}