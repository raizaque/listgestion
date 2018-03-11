package com.example.barzi.application.Utilisateur;

/**
 * Created by ROKIA on 08/02/2018.
 */

public class MaListe {
    public String titre;
    public String description;
    Element element_liste;

    public MaListe(String titre, String description) {
        this.titre = titre;
        this.description = description;

    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Element getElement_liste() {
        return element_liste;
    }

    public void setElement_liste(Element element_liste) {
        this.element_liste = element_liste;
    }
}
