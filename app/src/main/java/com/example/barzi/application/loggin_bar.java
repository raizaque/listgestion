package com.example.barzi.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barzi.application.Administrateur.Acceuil;
import com.example.barzi.application.Utilisateur.Profil_user;
import com.example.barzi.application.beans_DAO.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;


public class loggin_bar extends AppCompatActivity {
    private ProgressBar progressBar;
    private final Utilisateur user=new Utilisateur();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin_bar);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        progressBar.
                postInvalidate();
        Intent intent=getIntent();
        String email,motdepasse;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            email = bundle.getString("email")+"";
            motdepasse = bundle.getString("motdepasse")+"";
            requpération_donne_utilisateur(email,motdepasse);
        }
    }
    //methode pour gere la connexion d'un utilisateur
    public void requpération_donne_utilisateur(final String email, final String password){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,user.getApi_url(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("ConnectCompte");
                    if (jsonarray.length()!=0){
                        JSONObject conectuser =jsonarray.getJSONObject(0);
                        user.setId(conectuser.getString("id"));
                        user.setPseudo(conectuser.getString("pseudo"));
                        user.setPassword(conectuser.getString("password"));
                        user.setPermission(conectuser.getString("permission"));
                        user.setRole(conectuser.getString("role"));
                        Toast msg = Toast.makeText(getApplicationContext(), "Bienvenu "+user.getPseudo(), Toast.LENGTH_LONG);
                        msg.show();
                        if (user.getPermission().equals("1") && user.getRole().equals("1")){
                            Intent intent= new Intent(loggin_bar.this, Profil_user.class);
                            overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                            startActivity(intent);
                        }else
                        {
                            msg = Toast.makeText(getApplicationContext(), "Votre Compte n'est pas activé", LENGTH_LONG);
                            msg.show();
                            Intent intent= new Intent(loggin_bar.this, MainActivity.class);
                            overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast msg = Toast.makeText(getApplicationContext(), "Compte introuvable", LENGTH_LONG);
                        msg.show();
                        Intent intent= new Intent(loggin_bar.this, MainActivity.class);
                        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("method","connect");
                parameters.put("Email",email);
                parameters.put("PassWord",password);
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}

