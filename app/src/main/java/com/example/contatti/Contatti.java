package com.example.contatti;

class Contatti{
    String nickname;
    int numeroDiLavoro;
    int numeroPersonale;
    String emailLavoro;
    String emailPersonale;

    String getNickname(){return nickname;}
    int getNumeroDiLavoro(){return numeroDiLavoro;}
    int getNumeroPersonale(){return numeroPersonale;}
    String getEmailLavoro(){return emailLavoro;}
    String getEmailPersonale(){return emailPersonale;}

    void setNickname(String nickname){this.nickname=nickname;}
    void setNumeroDiLavoro(int numeroDiLavoro){this.numeroDiLavoro=numeroDiLavoro;}
    void setNumeroPersonale(int numeroPersonale){this.numeroPersonale=numeroPersonale;}
    void setEmailLavoro(String emailLavoro){this.emailLavoro=emailLavoro;}
    void setEmailPersonale(String emailPersonale){this.emailPersonale=emailPersonale;}

}