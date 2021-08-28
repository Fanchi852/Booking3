package com.example.booking3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.booking3.beans.Cliente;
import com.example.booking3.interfaces.UserContract;
import com.example.booking3.presenter.UserPresenter;
import com.example.booking3.view.CreateAccountActivity;
import com.example.booking3.view.HotelListActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserContract.View{

    private EditText    email,pass;
    private Button      regis,login;
    private ProgressBar progressBar;

    private UserContract.Presenter presenter;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        regis = findViewById(R.id.regis);
        login = findViewById(R.id.login);
        progressBar= findViewById(R.id.progressBar);

        presenter = new UserPresenter(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cliente = new Cliente();
                cliente.setContrasena(pass.getText().toString());
                cliente.setEmail(email.getText().toString());

                System.out.println("Main");
                presenter.login(cliente);

            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void sucessLogin(ArrayList<Cliente> cliente) {
        showMessageOnFinish("Success", "Usuario logueado correctamente");

        Bundle clienteData = new Bundle();
        clienteData.putInt("id_cliente", cliente.get(0).getId_cliente());

        Intent intent = new Intent(getApplicationContext(), HotelListActivity.class);
        intent.putExtras(clienteData);
        startActivity(intent);
    }

    @Override
    public void failureLogin(String message) {
        showMessageOnFinish("Error", "No se pudo loguear al usuario");
    }

    @Override
    public void onInitLoading() {
        progressBar.setMax(10);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showMessageOnFinish(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();

        /* GESTION DE CARGA */
        progressBar.setVisibility(View.GONE);
    }

}