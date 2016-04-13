package com.aro.defenseofatroth.WS;

/**
 * Created by Javier on 30/03/2016.
 */
public class User {
    private String nombre;
    private String mail;
    private int puntuacion;

    public String getNombre(){
        return nombre;
    }
    public User(String name,String email,int score){
        this.nombre=name;
        this.mail=email;
        this.puntuacion=score;
    }
    public int getPuntuacion(){return puntuacion;}
    public String getMail() {return mail;}
}
