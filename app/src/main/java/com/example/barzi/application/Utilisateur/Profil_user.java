package com.example.barzi.application.Utilisateur;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barzi.application.Administrateur.Acceuil;
import com.example.barzi.application.MainActivity;
import com.example.barzi.application.R;
import com.example.barzi.application.adapters.ListeAdapter;
import com.example.barzi.application.adapters.RecyclerItemClickListener;
import com.example.barzi.application.beans_DAO.Liste;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.barzi.application.adapters.RecyclerItemClickListener.*;

public class Profil_user extends AppCompatActivity {
    private Liste liste;
    private Context context;
    private RecyclerView maListe;
    private ArrayList<Liste> mesListes;
    private ListeAdapter mAdapter;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private TextView textView;
    private Utilisateur user;
    private SharedPreferences appSharedPrefs2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        ////////////////////////////////////////////////////////////////////////
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        textView=(TextView) findViewById(R.id.textView);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
        liste=new Liste();
        maListe = (RecyclerView) findViewById(R.id.maListe);
        //////////////////////////////////////////////////////////
        maListe.addOnItemTouchListener(
                new RecyclerItemClickListener(context, maListe ,new OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent_elt = new Intent(Profil_user.this, Elements_user.class);
                        intent_elt.putExtra("id_liste",mesListes.get(position).getId());
                        intent_elt.putExtra("iduserlist",mesListes.get(position).getId());
                        startActivity(intent_elt);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Intent intent_list = new Intent(Profil_user.this, Affiche_list.class);
                        intent_list.putExtra("id_liste",mesListes.get(position).getId());
                        intent_list.putExtra("iduserlist",mesListes.get(position).getId());
                        startActivity(intent_list);
                    }
                })
        );
        mesListes= new ArrayList<Liste>();
        //////////////////////////////////////////
        mesListes.clear();
        maListe.setAdapter(null);
        //////////////////////////////////////////
        appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] countries=getResources().getStringArray(R.array.array_recherche);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);
        spinner.setAdapter(adapter);
        select_liste_from_server();
        if(user.getRole().equals("0")){
            select_liste_from_server_admin();
        }
    }
    public void select_liste_from_server_admin(){
        mesListes.clear();
        maListe.setAdapter(null);
        //////////////////////////////////////////
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,"http://api-liste.alwaysdata.net/api/v1/listes", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    Log.d("ddeeeedddd",response.toString());
                    if (jsonarray.length()!=0) {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject liste_obj = jsonarray.getJSONObject(i);
                            liste.setId(liste_obj.getString("idliste"));
                            liste.setTitre(liste_obj.getString("title"));
                            liste.setDescription(liste_obj.getString("description"));
                            liste.setVisibilite(liste_obj.getString("visibility"));
                            liste.setIdutilisateur(liste_obj.getString("idUser"));
                            mesListes.add(new Liste(liste.getTitre(), liste.getDescription(),liste.getId()));
                        }
                        mAdapter = new ListeAdapter(mesListes);
                        maListe.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        maListe.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast msge = Toast.makeText(getApplicationContext(), "Votre Liste est vide", LENGTH_LONG);
                msge.show();
            }
        });
        requestQueue.add(request);
    }
    public void select_liste_from_server(){
        mesListes.clear();
        maListe.setAdapter(null);
                //////////////////////////////////////////
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,liste.getApi_url()+"/all/"+user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                 try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    Log.d("ddeeeedddd",response.toString());
                    if (jsonarray.length()!=0) {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject liste_obj = jsonarray.getJSONObject(i);
                            liste.setId(liste_obj.getString("idliste"));
                            liste.setTitre(liste_obj.getString("title"));
                            liste.setDescription(liste_obj.getString("description"));
                            liste.setVisibilite(liste_obj.getString("visibility"));
                            liste.setIdutilisateur(liste_obj.getString("idUser"));
                            mesListes.add(new Liste(liste.getTitre(), liste.getDescription(),liste.getId()));
                        }
                        mAdapter = new ListeAdapter(mesListes);
                        maListe.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        maListe.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast msge = Toast.makeText(getApplicationContext(), "Votre Liste est vide", LENGTH_LONG);
                msge.show();
            }
        });
        requestQueue.add(request);
    }
    public void ajouter_liste(View view) {
        if(!user.getId().equals("0")){
        Intent intent=new Intent(Profil_user.this,Ajouter_Liste.class);
        startActivity(intent);}
        else
            Toast.makeText(getApplicationContext(), "Vous n'avez pas de compte", LENGTH_LONG).show();

    }

    public void loggout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        appSharedPrefs2.edit().clear().commit();
        finish();
        startActivity(intent);
    }

    public void onBackPressed(){
        if (user.getRole().equals("0")){
                Intent intent= new Intent(Profil_user.this, Acceuil.class);
                overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                startActivity(intent);
        }
    }
}
