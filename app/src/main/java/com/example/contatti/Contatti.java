package com.example.contatti;

import java.util.ArrayList;

class Contatti{
    private String nickname;
    private String numeroDiTelefono;
    private ArrayList<String> extras;

    public String getNickname(){return nickname;}
    public String getNumeroDiTelefono(){return numeroDiTelefono;}

    public void setNickname(String nickname){this.nickname=nickname;}
    public void setNumeroDiTelefono(String numeroDiTelefono){this.numeroDiTelefono=numeroDiTelefono;}

    public Contatti(String nickname, String numeroDiTelefono) {
        this.nickname = nickname;
        this.numeroDiTelefono = numeroDiTelefono;
    }
}