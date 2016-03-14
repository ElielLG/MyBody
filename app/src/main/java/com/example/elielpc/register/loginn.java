package com.example.elielpc.register;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginn extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegistrarse3;
    EditText etUsuario, etContra;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);
        setContentView(R.layout.content_loginn);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContra = (EditText) findViewById(R.id.etContra);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrarse3 = (Button) findViewById(R.id.btnRegistrarse);
        userLocalStore = new UserLocalStore(this);

        btnLogin.setOnClickListener(this);
        btnRegistrarse3.setOnClickListener(this);

    }

    public boolean validaCampos() {

        if (!etUsuario.getText().toString().trim().equalsIgnoreCase("")
                || !etContra.getText().toString().trim().equalsIgnoreCase(""))
            return true;

        else
            return false;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (validaCampos()) {
                String username = etUsuario.getText().toString();
                String password = etContra.getText().toString();
                User user = new User(username, password);
                authenticate(user);
                } else
                    Toast.makeText(loginn.this, "Hay información por rellenar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnRegistrarse:
                if(authenticateReg() == false){
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(loginn.this);
                    dialogBuilder.setMessage("Error-Verifica que tu conexion \n funcione correctamente");
                    dialogBuilder.setPositiveButton("Ok", null);
                    dialogBuilder.show();
                }

                else {
                    Intent registerIntent = new Intent(loginn.this, registro.class);
                    startActivity(registerIntent);
                }
                break;
        }
    }

  /*

    public  void onClickRegistrarse(View view){
        Intent intent = new Intent(this,advertencia.class);
        startActivity(intent);
    */

    private void authenticate(User user) {
    /*El conectivity manager se encarga de revisar si tenemos conexion a internet*/
        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
        ManejadorPeticiones manejador = new ManejadorPeticiones(this);
        manejador.recuperarInfo(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });

        }else{
   /*Dialog para mensaje de falla de conectividad*/
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(loginn.this);
            dialogBuilder.setMessage("Error-Verifica que tu conexion \n funcione correctamente");
            dialogBuilder.setPositiveButton("Ok", null);
            dialogBuilder.show();
        }
    }

    private boolean authenticateReg(){
        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){


            return  true;

        }else{
   /*Dialog para mensaje de falla de conectividad*/
           return false;
        }
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(loginn.this);
        dialogBuilder.setMessage("Usuario o contraseña no validos");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));


    }


}
