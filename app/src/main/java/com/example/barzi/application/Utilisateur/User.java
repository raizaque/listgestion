package com.example.barzi.application.Utilisateur;

/**
 * Created by ROKIA on 08/02/2018.
 */

public class User {
    private String nom;
   private String motDepasse;
    MaListe liste_user;

    public User(String nom, String motDepasse, MaListe liste_user) {
        this.nom = nom;
        this.motDepasse = motDepasse;
        this.liste_user = liste_user;
    }

}
