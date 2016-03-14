package com.example.elielpc.register;


public class User {

    String name, username, password;
    int age;
    double size, peso;
    String objetivo, sexo;

    public User(String username, String password,String name, int age, double size, double peso, String objetivo, String sexo){
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.size = size;
        this.peso = peso;
        this.objetivo = objetivo;
        this.sexo = sexo;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.age = -1;
        this.name = "";
        this.size = -1;
        this.peso = -1;
        this.objetivo = "";
        this.sexo = "";
    }


    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public double getSize() {
        return size;
    }

    public double getPeso() {
        return peso;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getSexo() {
        return sexo;
    }
}
