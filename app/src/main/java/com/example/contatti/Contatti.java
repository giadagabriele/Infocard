package com.example.contatti;

import java.util.ArrayList;

class Contatti{
    private String nickname;
    private String numeroDiTelefono;
    private String email;
    private ArrayList<String> extras;

    public String getNickname(){return nickname;}
    public String getNumeroDiTelefono(){return numeroDiTelefono;}
    public String getEmail(){return email;}
    public ArrayList<String> getExtras(){return extras;}

    public void setNickname(String nickname){this.nickname=nickname;}
    public void setNumeroDiTelefono(String numeroDiTelefono){this.numeroDiTelefono=numeroDiTelefono;}
    public void setEmail(String email){this.email=email;}
    public void setExtras(ArrayList<String> extras){this.extras = extras;}

    public Contatti(String nickname, String numeroDiTelefono, String email, ArrayList<String> extras) {
        this.nickname = nickname;
        this.numeroDiTelefono = numeroDiTelefono;
        this.email=email;
        this.extras = extras;
    }
}