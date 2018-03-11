package com.example.barzi.application.Utilisateur;

import java.util.Date;

/**
 * Created by ROKIA on 08/02/2018.
 */

public class Element {
    public String titre_element;
    public String description_element;
    public String Statut_optionnel;
    public Etiquette tag;
    public Date date_creation;
    public Date dateDerniere_modif;

    public Element(String titre_element, String description_element, String statut_optionnel, Etiquette tag, Date date_creation, Date dateDerniere_modif) {
        this.titre_element = titre_element;
        this.description_element = description_element;
        Statut_optionnel = statut_optionnel;
        this.tag = tag;
        this.date_creation = date_creation;
        this.dateDerniere_modif = dateDerniere_modif;
    }

    public String getTitre_element() {
        return titre_element;
    }

    public String getDescription_element() {
        return description_element;
    }

    public String getStatut_optionnel() {
        return Statut_optionnel;
    }

    public Etiquette getTag() {
        return tag;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public Date getDateDerniere_modif() {
        return dateDerniere_modif;
    }

    public void setTitre_element(String titre_element) {
        this.titre_element = titre_element;
    }

    public void setDescription_element(String description_element) {
        this.description_element = description_element;
    }

    public void setStatut_optionnel(String statut_optionnel) {
        Statut_optionnel = statut_optionnel;
    }
}
