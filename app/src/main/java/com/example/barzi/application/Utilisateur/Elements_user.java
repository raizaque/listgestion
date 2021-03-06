package com.example.barzi.application.Utilisateur;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barzi.application.MainActivity;
import com.example.barzi.application.R;
import com.example.barzi.application.adapters.ElementAdapter;
import com.example.barzi.application.adapters.RecyclerItemClickListener;
import com.example.barzi.application.beans_DAO.Element;
import com.example.barzi.application.beans_DAO.Liste;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.example.barzi.application.loggin_bar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.barzi.application.adapters.RecyclerItemClickListener.*;
public class Elements_user extends AppCompatActivity {
    private RecyclerView Element;
    private ArrayList<com.example.barzi.application.beans_DAO.Element> mesElements;
    private ElementAdapter eAdapter;
    private Liste liste;
    private Element element;
    private String id_list="";
    private TextView textView;
    private Utilisateur user;
    private SharedPreferences appSharedPrefs2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_user);
        liste=new Liste();
        element=new Element();
        ///////////////////////////////////////////////////////////////////////
        textView=(TextView) findViewById(R.id.textView);
        appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");
        ////////////////////////////////////////////////////////
        Element = (android.support.v7.widget.RecyclerView) findViewById(R.id.maListe);
        Element.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), Element ,new OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent_list = new Intent(Elements_user.this, Affiche_elt.class);
                        intent_list.putExtra("id_element",mesElements.get(position).getId());
                        intent_list.putExtra("iduserlist",liste.getIdutilisateur());
                        intent_list.putExtra("idlist",liste.getId());

                        startActivity(intent_list);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        ///////////////////////////////////////////////////////////////////////
        mesElements= new ArrayList<>();
        mesElements.clear();
        eAdapter = new ElementAdapter(mesElements);
        Element.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        Element.setAdapter(null);
        //////////////////////////////////////////////////
        Intent i=getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            id_list = bundle.getString("id_liste")+"";
            liste.setIdutilisateur((String)bundle.get("iduserlist"));
            liste.setId(id_list);
            remplire_liste_delement();
            Log.d("hello",id_list);
        }
    }
    private void remplire_liste_delement() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,element.getApi_url()+"/"+liste.getId()+"/elements", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    if (jsonarray.length()!=0) {
                        mesElements.clear();
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject liste_obj = jsonarray.getJSONObject(i);
                            Log.d("elemnt",liste_obj.toString());
                            element.setId(liste_obj.getString("idelements"));
                            element.setDate_creation(liste_obj.getString("date_creation"));
                            element.setDateDerniere_modif(liste_obj.getString("date_modif"));
                            element.setTitre_element(liste_obj.getString("titre"));
                            element.setDescription_element(liste_obj.getString("description"));
                            element.setStatut_optionnel(liste_obj.getString("statut"));
                            element.setIdliste(liste_obj.getString("idListe"));
                            Log.d("erre",element.toString());
                            mesElements.add(new Element(element));
                        }
                        eAdapter = new ElementAdapter(mesElements);
                        Element.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        Element.setAdapter(eAdapter);
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
    public void ajouter_element(View view) {
        if (!user.getId().equals("0")) {
            Intent intent = new Intent(Elements_user.this, Ajouter_Element.class);
            intent.putExtra("id_element", liste.getId());
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "Vous n'avez pas de compte", LENGTH_LONG).show();
    }

    public void loggout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        appSharedPrefs2.edit().clear().commit();
        finish();
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){
        Intent intent= new Intent(Elements_user.this, Profil_user.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);

    }
}

