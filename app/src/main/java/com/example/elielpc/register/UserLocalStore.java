package com.example.elielpc.register;

import android.content.Context;
import android.content.SharedPreferences;


public class UserLocalStore {

    float peso;
    float size;
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("name", user.name);
        spEditor.putInt("age", user.age);
        spEditor.putFloat("peso", peso = (float)user.peso);
        spEditor.putFloat("size", size= (float)user.size);
        spEditor.putString("sexo", user.sexo);
        spEditor.putString("objetivo",user.objetivo);
        spEditor.commit();
    }

    public User getLoggedInUser() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String name = userLocalDatabase.getString("name", "");
        int age = userLocalDatabase.getInt("age", -1);
        Float peso = userLocalDatabase.getFloat("peso", -1);
        Float size = userLocalDatabase.getFloat("size", -1);
        String sexo = userLocalDatabase.getString("sexo", "");
        String objetivo = userLocalDatabase.getString("objetivo","");

        User storedUser = new User(username,password,name,age,size,peso,objetivo,sexo);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEdior = userLocalDatabase.edit();
        spEdior.putBoolean("loggedIn", loggedIn);
        spEdior.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("LoggedIn",false)==true){
            return true;
        }
        else {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
