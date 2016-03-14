package com.example.elielpc.register;
/*Esta clase se encarga de manejar las peticiones al servidor en hilos en segundo plano*/
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ManejadorPeticiones {
    /*Direccion del servidor*/
    public static final String SERVER_ADDRESS = "http://k120.comxa.com/";
    ProgressDialog progressDialog;
    Context c;
   public  ManejadorPeticiones(Context c) {
        this.c = c;
       /*Creamos un processDialog*/
        progressDialog = new ProgressDialog(c);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Procesando...");
        progressDialog.setMessage("Porfavor espera...");
    }

    /*Almacena los datos introducidos en la pantalla de registro al servidor*/
    public void almacenarInfo(User usuario,GetUserCallback userCallBack) {
        progressDialog.show();
        new AlmacenarInfoUsuario(usuario,userCallBack).execute();
    }
    /*Recupera los datos del usuario del servidor*/
    public void recuperarInfo(User usuario,GetUserCallback userCallBack) {


            progressDialog.show();
            new RecuperarInfoUsuario(usuario,userCallBack).execute();

    }

    public class RecuperarInfoUsuario extends AsyncTask<Void, Void, User> {
        User usuario;
        GetUserCallback userCallBack;
        RecuperarInfoUsuario(User usuario,GetUserCallback userCallBack) {
            this.usuario = usuario;
            this.userCallBack = userCallBack;
        }
        @Override
        protected User doInBackground(Void... params) {
            User UsuarioDevuelto = null;

            /*Aqui se almacenan los datos antes de ser enviados al servidor*/
            Map<String,String> dataToSend=new HashMap<>();
            dataToSend.put("username",usuario.getUsername());
            dataToSend.put("password",usuario.getPassword());
            /*Se codifican al formato requerido por el metodo de envio POST*/
            String encodedStr = getEncodedData(dataToSend);
            try {
                /*Iniciamos al conexion con el archivo php del servidor*/
            URL url = new URL(SERVER_ADDRESS + "recuperarUsuario.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                /*Habilitamos que se reciban datos y se use POST como metodo de envio*/
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                /*Establecemos tiempos de espera para la respuesta del servidor*/
                con.setReadTimeout(10000);
                con.setConnectTimeout(15000);
                /*Se crea un writer y se le pasa getOutputStream de la conexion par apoder enviar los datos*/
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(encodedStr);
                writer.flush();
                writer.close();
                //con.disconnect();
                /*Aqui se se crea un reader con la respuesta del servidor getInputStream*/
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                /*leemos los datos*/
                while((line = reader.readLine()) != null) {
                    sb.append(line);}/*linea a linea*/
                    line = sb.toString();/*Linea completa*/
                    reader.close();
                    try {
                        /*El servidor responde con formato JSON*/
                        /*Se van obteniendo los datos devueltos*/
                    JSONObject jObject = new JSONObject(line);
                    if (jObject.length() != 0) {
                    String name = jObject.getString("name");
                    int age = jObject.getInt("age");
                    double size=jObject.getDouble("size");
                    double peso=jObject.getDouble("peso");
                    String objetivo=jObject.getString("objetivo");
                    String sexo=jObject.getString("sexo");
                    /*Los alamcenamos en un objeto user*/
                    UsuarioDevuelto = new User(usuario.getUsername(), usuario.getPassword(),name, age, size,  peso, objetivo, sexo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }}catch (Exception e){
                e.printStackTrace();}
            return UsuarioDevuelto;}
        @Override
        protected void onPostExecute(User UsuarioDevuelto) {
            super.onPostExecute(UsuarioDevuelto);
            progressDialog.dismiss();
            userCallBack.done(UsuarioDevuelto);

        }}

    public class AlmacenarInfoUsuario extends AsyncTask<Void, Void, Void> {
        User usuario;
        GetUserCallback userCallBack;
        AlmacenarInfoUsuario(User usuario,GetUserCallback userCallBack) {
            this.usuario = usuario;
            this.userCallBack = userCallBack;
        }
        @Override
        protected Void doInBackground(Void... params) {
            /*Aqui se almacenan los datos antes de ser enviados al servidor*/
            Map<String,String> dataToSend=new HashMap<>();
            dataToSend.put("username",usuario.getUsername());
            dataToSend.put("password",usuario.getPassword());
            dataToSend.put("name",usuario.getName());
            dataToSend.put("age",usuario.getAge()+"");
            dataToSend.put("size",usuario.getSize()+"");
            dataToSend.put("peso",usuario.getPeso()+"");
            dataToSend.put("objetivo",usuario.getObjetivo());
            dataToSend.put("sexo",usuario.getSexo());
              /*Se codifican al formato requerido por el metodo de envio POST*/
            String encodedStr = getEncodedData(dataToSend);
            try {
                /*Iniciamos al conexion con el archivo php del servidor*/
                URL url = new URL(SERVER_ADDRESS + "registrarUsuario.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                /*Habilitamos que se reciban datos y se use POST como metodo de envio*/
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                /*Establecemos tiempos de espera para la respuesta del servidor*/
                con.setReadTimeout(10000);
                con.setConnectTimeout(15000);
                /*Se crea un writer y se le pasa getOutputStream de la conexion par apoder enviar los datos*/
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(encodedStr);
                writer.flush();
                writer.close();
                /*Obtenemos respuesta*/
                con.getInputStream();
                //con.disconnect();
            }catch (Exception e){
                e.printStackTrace();}
            return null;}
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            userCallBack.done(null);


        }}

/*Metodo para darle formato alos datos para que POST los pueda enviar*/
    private String getEncodedData(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for(String key : data.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(data.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(sb.length()>0)
                sb.append("&");
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }


}
