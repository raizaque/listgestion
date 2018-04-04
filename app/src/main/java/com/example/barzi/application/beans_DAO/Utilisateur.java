package com.example.barzi.application.beans_DAO;

/**
 * Created by barzi on 12/03/2018.
 */

public class Utilisateur {

    private String id;
    private String pseudo;
    private String password;
    private String permission;
    private String role;

    public Utilisateur() {

    }

    public String getApi_url() {
        return api_url;
    }
    private String api_url="https://projet-liste2018.000webhostapp.com/api/v1/utilisateurs";
    public Utilisateur(String id, String pseudo, String password, String permission, String role) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.permission = permission;
        this.role = role;
    }

    public Utilisateur(String id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
