package com.example.booking3.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.booking3.MainActivity;
import com.example.booking3.R;
import com.example.booking3.beans.Cliente;
import com.example.booking3.interfaces.UserContract;
import com.example.booking3.presenter.UserPresenter;

public class CreateAccountActivity extends AppCompatActivity implements UserContract.View{

    EditText addres,name,email,pass,tlf,dni,last_name;
    Button regisbt;

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
    public void sucessLogin(Boolean login) {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    @Override
    public void failureLogin(String message) {

        System.out.println(message);

    }

    @Override
    public void onInitLoading() {

    }
}