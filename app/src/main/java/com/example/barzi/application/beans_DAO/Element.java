package com.example.barzi.application.beans_DAO;

/**
 * Created by ROKIA on 08/02/2018.
 */

public class Element {

    private String api_url="http://api-liste.alwaysdata.net/api/v1/listes";
    
    private String id;
    private String titre_element;
    private String description_element;
    private String Statut_optionnel;
    private Etiquette tag;
    private String date_creation;
    private String dateDerniere_modif;
    private String idliste;
    public Element(String id, String titre_element, String description_element, String statut_optionnel, Etiquette tag, String date_creation, String dateDerniere_modif) {
        this.id = id;
        this.titre_element = titre_element;
        this.description_element = description_element;
        Statut_optionnel = statut_optionnel;
        this.tag = tag;
        this.date_creation = date_creation;
        this.dateDerniere_modif = dateDerniere_modif;
    }

    public String getIdliste() {
        return idliste;
    }

    public void setIdliste(String idliste) {
        this.idliste = idliste;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTag(Etiquette tag) {
        this.tag = tag;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public void setDateDerniere_modif(String dateDerniere_modif) {
        this.dateDerniere_modif = dateDerniere_modif;
    }

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }
    public Element(String titre_element, String description_element){
        this.titre_element = titre_element;
        this.description_element = description_element;
    }

    public Element(String titre_element, String description_element, String statut_optionnel, Etiquette tag, String date_creation, String dateDerniere_modif) {
        this.titre_element = titre_element;
        this.description_element = description_element;
        this.Statut_optionnel = statut_optionnel;
        this.tag = tag;
        this.date_creation = date_creation;
        this.dateDerniere_modif = dateDerniere_modif;
    }

    public Element() {

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

    public String getDate_creation() {
        return date_creation;
    }

    public String getDateDerniere_modif() {
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
