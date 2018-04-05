package com.example.barzi.application.beans_DAO;

/**
 * Created by barzi on 05/04/2018.
 */

public class Etiquéte {
    private String idetiquette;
    private String tag;
    private String idUser;
    private String api_url_etiquéte="http://api-liste.alwaysdata.net/api/v1/identifies/";
    public String getIdetiquette() {
        return idetiquette;
    }
    public void setIdetiquette(String idetiquette) {
        this.idetiquette = idetiquette;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getApi_url_etiquéte() {
        return api_url_etiquéte;
    }

    public void setApi_url_etiquéte(String api_url_etiquéte) {
        this.api_url_etiquéte = api_url_etiquéte;
    }
}
