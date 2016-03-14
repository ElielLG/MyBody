package com.example.elielpc.register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class registro extends AppCompatActivity implements View.OnClickListener {


    Button btnRegistro2;
    EditText etNombreUsuario2, etNombre2, etContra2, etEdad2, etEstatura2, etPeso2,etrepContra2;
    Spinner spSexo2, objetivo2;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registro);

        etNombreUsuario2 = (EditText) findViewById(R.id.etNombreUsuario);
        etNombre2 = (EditText) findViewById(R.id.etNombre);
        etContra2 = (EditText) findViewById(R.id.etContraseña2);
        etrepContra2 = (EditText) findViewById(R.id.etRepContraseña);
        etEdad2 = (EditText) findViewById(R.id.etEdad);
        etEstatura2 = (EditText) findViewById(R.id.etEstatura);
        etPeso2 = (EditText) findViewById(R.id.etPeso);
        spSexo2 = (Spinner) findViewById(R.id.sexo);
        btnRegistro2 = (Button) findViewById(R.id.btnRegistrarse2);
        objetivo2 = (Spinner) findViewById(R.id.objetivo);
        btnRegistro2.setOnClickListener(this);

    }




private boolean validaCampos() {

        if (!etNombreUsuario2.getText().toString().trim().equalsIgnoreCase("")
                || !etContra2.getText().toString().trim().equalsIgnoreCase("")
                || !etNombre2.getText().toString().trim().equalsIgnoreCase("")
                || !etEdad2.getText().toString().trim().equalsIgnoreCase("")
                || !etEstatura2.getText().toString().trim().equalsIgnoreCase("")
                || !etPeso2.getText().toString().trim().equalsIgnoreCase("")
                || !etrepContra2.getText().toString().trim().equalsIgnoreCase("")
                )

            return true;


        else
            return false;




    }

    private boolean validaPass() {
        if(etrepContra2.getText().toString().trim().equals(etContra2.getText().toString().trim()))
            return true;

    else
            return false;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistrarse2:
                if (validaCampos()) {


                    if (validaPass()) {
                        String username = etNombreUsuario2.getText().toString();
                        String password = etContra2.getText().toString();
                        String name= etNombre2.getText().toString();
                        int edad = Integer.parseInt(etEdad2.getText().toString());
                        double estatura = Double.parseDouble(etEstatura2.getText().toString());
                        double peso = Double.parseDouble(etPeso2.getText().toString());
                        double imc = peso/(estatura*estatura);
                        String objetivo = String.valueOf(objetivo2.getSelectedItem());
                        String sexo = String.valueOf(spSexo2.getSelectedItem());

                        if(edad > 15 && edad < 46) {
                            if(imc < 18.5 && objetivo.equals("Adelgazar")){
                                Toast.makeText(registro.this, "Debido a tu Indice de Masa Corporal tu objetivo debe ser Aumentar masa muscular", Toast.LENGTH_SHORT).show();
                            }
                            else if(imc > 24.9 && objetivo.equals("Aumentar masa muscular")){

                            }
                            else {
                                user = new User(username, password, name, edad, estatura, peso, objetivo, sexo);
                                registerUser(user);
                            }
                        }
                        else{
                            Toast.makeText(registro.this, "El limite de edad es entre 15 a 45 años", Toast.LENGTH_SHORT).show();
                        }
                    } else
                    Toast.makeText(registro.this, "Asegurate de que la contraseña sea correcta", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(registro.this, "Porfavor rellena todos los campos", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void registerUser(User user) {
   /*Dialog para confirmar el envio*/
        AlertDialog.Builder builder = new AlertDialog.Builder(registro.this);

        builder.setMessage("¿Los datos son correctos?")
                .setTitle("Confirmacion")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ManejadorPeticiones manejador = new ManejadorPeticiones(registro.this);
                        manejador.almacenarInfo(getUser(), new GetUserCallback() {
                            @Override
                            public void done(User returnedUser) {

                            }
                        });

                        Intent loginIntent = new Intent(registro.this, loginn.class);
                        startActivity(loginIntent);
                        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        builder.show();

    }

    private User getUser(){
        return this.user;
    }

}
