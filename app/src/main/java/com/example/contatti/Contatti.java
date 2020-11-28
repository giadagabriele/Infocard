package com.example.contatti;

import android.widget.EditText;

class Contatti{
    private String nickname;
    private int numeroDiTelefono;
    private String email;

    public String getNickname(){return nickname;}
    public int getNumeroDiTelefono(){return numeroDiTelefono;}
    public String getEmail(){return email;}

    public void setNickname(String nickname){this.nickname=nickname;}
    public void setNumeroDiTelefono(int numeroDiTelefono){this.numeroDiTelefono=numeroDiTelefono;}
    public void setEmail(String email){this.email=email;}

    public Contatti(String nickname, int numeroDiTelefono, String email) {
        this.nickname = nickname;
        this.numeroDiTelefono = numeroDiTelefono;
        this.email = email;
    }
}