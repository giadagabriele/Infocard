package com.example.contatti;

import android.widget.EditText;

class Contatti{
    String nickname="Giada Gabriele";
    int numeroDiTelefono;
    String email;

    String getNickname(){return nickname;}
    int getNumeroDiTelefono(){return numeroDiTelefono;}
    String getEmail(){return email;}

    void setNickname(String nickname){this.nickname=nickname;}
    void setNumeroDiTelefono(int numeroDiTelefono){this.numeroDiTelefono=numeroDiTelefono;}
    void setEmail(String email){this.email=email;}

}