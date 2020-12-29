package com.example.contatti;

import java.util.ArrayList;

class Contatti{
    private String foto;
    private String nickname;
    private String nome;
    private String cognome;
    private String numeroDiTelefono;
    private String email;
    private ArrayList<String> extras;
    private String UID;

    public String getUID(){return UID;}
    public String getFoto(){return foto;}
    public String getNickname(){return nickname;}
    public String getNome(){return nome;}
    public String getCognome(){return cognome;}
    public String getNumeroDiTelefono(){return numeroDiTelefono;}
    public String getEmail(){return email;}
    public ArrayList<String> getExtras(){return extras;}

    public void setFoto(String foto){this.foto=foto;}
    public void setNickname(String nickname){this.nickname=nickname;}
    public void setNome(String nome){this.nome=nome;}
    public void setCognome(String cognome){this.cognome=cognome;}
    public void setNumeroDiTelefono(String numeroDiTelefono){this.numeroDiTelefono=numeroDiTelefono;}
    public void setEmail(String email){this.email=email;}
    public void setExtras(ArrayList<String> extras){this.extras = extras;}

    public Contatti(String UID,String foto, String nickname) {
        this.UID=UID;
        this.foto=foto;
        this.nickname = nickname;
    }
    public Contatti(String foto, String nickname, String nome, String cognome, String numeroDiTelefono, String email, ArrayList<String> extras) {
        this.foto=foto;
        this.nickname = nickname;
        this.nome=nome;
        this.cognome=cognome;
        this.numeroDiTelefono = numeroDiTelefono;
        this.email=email;
        this.extras = extras;
    }
}