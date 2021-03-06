package com.example.barzi.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barzi.application.Utilisateur.Ajouter_Element;
import com.example.barzi.application.Utilisateur.Elements_user;
import com.example.barzi.application.Utilisateur.Profil_user;
import com.example.barzi.application.beans_DAO.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class inscription extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Utilisateur user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.motdepasse);
        user=new Utilisateur();
    }
    public void hello(View view) {
    }
    public void inscription(View view) {
        if (email.getText().toString().equals("") && password.getText().toString().equals("")) {
            Toast msg = Toast.makeText(getApplicationContext(), "Un des champs est vide", Toast.LENGTH_LONG);
            msg.show();
        } else {
            insertion_donnee_utilisateur(email.getText().toString(),password.getText().toString());
        }
    }
    public void insertion_donnee_utilisateur(final String email, final String password){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,user.getApi_url()+"?pseudo="+email+"&password="+password+"&permission=0&role=0", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String reponses=response.toString();
                Toast msg = Toast.makeText(getApplicationContext(), reponses, LENGTH_LONG);
                msg.show();

                    Intent intent= new Intent(inscription.this, MainActivity.class);
                    overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                    startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast msgs = Toast.makeText(getApplicationContext(), "Compte deja existant", LENGTH_LONG);
                msgs.show();
            }
        }) ;
        requestQueue.add(request);
    }
    public void onBackPressed(){
        Intent intent = new Intent(inscription.this, MainActivity.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);
    }
}
