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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.booking3.R;
import com.example.booking3.adapter.HotelAdapter;
import com.example.booking3.beans.Categoria;
import com.example.booking3.beans.Hotel;
import com.example.booking3.interfaces.CategoriaContract;
import com.example.booking3.interfaces.HotelContract;
import com.example.booking3.presenter.CategoriaPresenter;
import com.example.booking3.presenter.HotelPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HotelListActivity extends AppCompatActivity implements HotelContract.View, CategoriaContract.View {

    private RecyclerView recyclre;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Button btfiltro;
    private EditText fechaEntradaEdit, fechaSalidaEdit, numeroDePersonas, DireccionEdit;
    private Spinner filtrospinner;

    private HotelPresenter hotelPresenter;
    private CategoriaPresenter categoriaPresenter;
    private HashMap<Integer, Categoria> categoriasMap;
    private HashMap<Integer, Hotel> hotelesMap;
    private HashMap<String, String> hotelFiltro;


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
        hotelPresenter = new HotelPresenter(this);
        categoriasMap = new HashMap<>();
        hotelesMap = new HashMap<>();
        lManager = new LinearLayoutManager(this);

        recyclre.setLayoutManager(lManager);
        recyclre.setHasFixedSize(true);

        hotelPresenter.getHotel();

        categoriaPresenter.getCategorias();

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

                hotelPresenter.getHotel();
            }
        });

    }

    @Override
    public void sucessLstHotel(ArrayList<Hotel> lstHoteles) {
        System.out.println("terminada la peticion de hoteles: "+lstHoteles.toString());
        for (Hotel hotel : lstHoteles) {
            hotelesMap.put(hotel.getId_hotel(), hotel);
        }
        if(!categoriasMap.isEmpty()){
            parseHoteles();
        }
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
    public void onInitLoading() {
        progressBar.setMax(10);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void sucessGetCategorias(ArrayList<Categoria> lstCategoria) {
        System.out.println("terminada la peticion de categorias: "+lstCategoria.toString());
        for (Categoria categoria : lstCategoria) {
            categoriasMap.put(categoria.getId_categoria(), categoria);
        }
        if(!hotelesMap.isEmpty()){
            parseHoteles();
        }
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

    public void parseHoteles() {
        ArrayList<Hotel> lstHoteles = new ArrayList<>();
        for (Hotel hotel : hotelesMap.values()) {
            hotel.setCategoria(categoriasMap.get(hotel.getId_categoria()));
            lstHoteles.add(hotel);
        }

        for()

        adapter = new HotelAdapter(lstHoteles,this);
        recyclre.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

}