package com.example.barzi.application.beans_DAO;

/**
 * Created by barzi on 15/03/2018.
 */

public class Liste {
    private String api_url="http://api-liste.alwaysdata.net/api/v1/listes";
    private String id;
    private String titre;
    private String description;
    private String visibilite;
    private String idutilisateur;

    public Liste(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }
    public Liste(String titre, String description,String id) {
        this.titre = titre;
        this.description = description;
        this.id=id;
    }

    public Liste() {

    }

    public String getApi_url() {
        return api_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
    }

    public String getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(String idutilisateur) {
        this.idutilisateur = idutilisateur;
    }
}
