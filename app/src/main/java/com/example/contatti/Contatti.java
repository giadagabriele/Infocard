package com.example.contatti;

import android.widget.EditText;

class Contatti{
    private String nickname;
    private String numeroDiTelefono;
    private String email;

    public String getNickname(){return nickname;}
    public String getNumeroDiTelefono(){return numeroDiTelefono;}
    public String getEmail(){return email;}

    public void setNickname(String nickname){this.nickname=nickname;}
    public void setNumeroDiTelefono(String numeroDiTelefono){this.numeroDiTelefono=numeroDiTelefono;}
    public void setEmail(String email){this.email=email;}

    public Contatti(String nickname, String numeroDiTelefono, String email) {
        this.nickname = nickname;
        this.numeroDiTelefono = numeroDiTelefono;
        this.email = email;
    }
}