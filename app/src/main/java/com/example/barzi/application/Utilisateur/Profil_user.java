package com.example.barzi.application.Utilisateur;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Liste;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.barzi.application.Utilisateur.RecyclerItemClickListener.*;

public class Profil_user extends AppCompatActivity {
    private Liste liste;
    private Context context;
    private RecyclerView maListe;
    private ArrayList<Liste> mesListes;
    private ListeAdapter mAdapter;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
        liste=new Liste();
        maListe = (RecyclerView) findViewById(R.id.maListe);
        maListe.addOnItemTouchListener(
                new RecyclerItemClickListener(context, maListe ,new OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast msg = Toast.makeText(getApplicationContext(), "Bienvenu "+mAdapter.get_list_numero(position).getTitre(), Toast.LENGTH_LONG);
                        msg.show();

                        Intent intent_elt = new Intent(Profil_user.this, Elements_user.class);
                        startActivity(intent_elt);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Toast msg = Toast.makeText(getApplicationContext(), "Bienvenu hurrdurr", Toast.LENGTH_LONG);
                        msg.show();
                        Intent intent_list = new Intent(Profil_user.this, Affiche_list.class);
                        startActivity(intent_list);
                    }
                })
        );
        mesListes= new ArrayList<Liste>();
        mesListes.add(new Liste("Course", "achat de ramadhan"));
        mesListes.add(new Liste("Course", "achat de ramadhan"));
        mesListes.add(new Liste("Course", "achat de ramadhan"));
        mesListes.add(new Liste("Course", "achat de ramadhan"));
        mesListes.add(new Liste("Course", "achat de ramadhan"));
        mesListes.add(new Liste("Course", "achat de ramadhan"));
        mAdapter = new ListeAdapter(mesListes);
        maListe.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        maListe.setAdapter(mAdapter);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] countries=getResources().getStringArray(R.array.array_recherche);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);
        spinner.setAdapter(adapter);
        select_liste_from_server();
    }
    public void select_liste_from_server(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST,liste.getApi_url(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("select");
                    if (jsonarray.length()!=0) {
                        mesListes.clear();
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject liste_obj = jsonarray.getJSONObject(i);
                            liste.setId(liste_obj.getString("id"));
                            liste.setTitre(liste_obj.getString("titre"));
                            liste.setDescription(liste_obj.getString("description"));
                            liste.setVisibilite(liste_obj.getString("visibilité"));
                            liste.setIdutilisateur(liste_obj.getString("id_utilisateur"));
                            mesListes.add(new Liste(liste.getTitre(), liste.getDescription(),liste.getId()));
                        }
                        mAdapter = new ListeAdapter(mesListes);
                        maListe.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        maListe.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                    }
                    else{
                        Toast msge = Toast.makeText(getApplicationContext(), "Votre Liste est vide", LENGTH_LONG);
                        msge.show();
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
                parameters.put("method","select");
                parameters.put("Email","rokia");
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
