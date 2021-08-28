package com.example.booking3.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.booking3.MainActivity;
import com.example.booking3.R;
import com.example.booking3.beans.Cliente;
import com.example.booking3.interfaces.UserContract;
import com.example.booking3.presenter.UserPresenter;

import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity implements UserContract.View{

    EditText addres,name,email,pass,tlf,dni,last_name;
    Button regisbt;
    ProgressBar progressBar;

    UserContract.Presenter presenter;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email = findViewById(R.id.campo_regis_email);
        addres = findViewById(R.id.campo_regis_address);
        name = findViewById(R.id.campo_regis_name);
        last_name = findViewById(R.id.campo_regis_last_name);
        pass = findViewById(R.id.campo_regis_pass);
        tlf = findViewById(R.id.campo_regis_tlf);
        dni = findViewById(R.id.campo_regis_dni);
        regisbt = findViewById(R.id.regisbt);
        progressBar= findViewById(R.id.progressBar);

        presenter = new UserPresenter(this);

        regisbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                cliente = new Cliente();
                cliente.setContrasena(pass.getText().toString());
                cliente.setEmail(email.getText().toString());
                cliente.setDireccion(addres.getText().toString());
                cliente.setDni(dni.getText().toString());
                cliente.setNombre(name.getText().toString());
                cliente.setApellido(last_name.getText().toString());
                cliente.setTelefono(Integer.parseInt(tlf.getText().toString()));

                System.out.println("create account");
                presenter.register(cliente);

            }
        });

    }

    @Override
    public void sucessLogin(ArrayList<Cliente> cliente) {

        showMessageOnFinish("Success", "Usuario registrado correctamente");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void failureLogin(String message) {
        showMessageOnFinish("Error", "No se pudo registrar al usuario");
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