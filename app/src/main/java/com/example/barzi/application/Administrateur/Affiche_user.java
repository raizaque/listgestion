package com.example.barzi.application.Administrateur;

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
import com.example.barzi.application.MainActivity;
import com.example.barzi.application.R;
import com.example.barzi.application.Utilisateur.Affiche_list;
import com.example.barzi.application.Utilisateur.Ajouter_Element;
import com.example.barzi.application.Utilisateur.Elements_user;
import com.example.barzi.application.adapters.ListeAdapter;
import com.example.barzi.application.adapters.RecyclerItemClickListener;
import com.example.barzi.application.adapters.UserAdapter;
import com.example.barzi.application.beans_DAO.Liste;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class Affiche_user extends AppCompatActivity {
    private Utilisateur user;
    private Context context;
    private RecyclerView mesUser;
    private ArrayList<Utilisateur> users;
    private UserAdapter mAdapter;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private TextView textView;
    private SharedPreferences appSharedPrefs2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_user);
        textView=(TextView) findViewById(R.id.textView);
        appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
        user=new Utilisateur();
        mesUser = (RecyclerView) findViewById(R.id.muser);
        mesUser.addOnItemTouchListener(
                new RecyclerItemClickListener(context, mesUser ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent_list = new Intent(Affiche_user.this, Gestion_user.class);
                        intent_list.putExtra("id_User",users.get(position).getId());
                        startActivity(intent_list);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );
        users= new ArrayList<Utilisateur>();
        mesUser.setAdapter(null);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] countries=getResources().getStringArray(R.array.array_recherche);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);
        spinner.setAdapter(adapter);
        select_users_from_server_admin();
    }

    public void select_users_from_server_admin(){
        //////////////////////////////////////////
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,user.getApi_url(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    if (jsonarray.length()!=0) {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject liste_obj = jsonarray.getJSONObject(i);
                            user.setId(liste_obj.getString("idUser"));
                            user.setPseudo(liste_obj.getString("pseudo"));
                            user.setPassword(liste_obj.getString("password"));
                            user.setPermission(liste_obj.getString("permission"));
                            user.setRole(liste_obj.getString("role"));
                            users.add(new Utilisateur(user));
                        }
                        mAdapter = new UserAdapter(users);
                        mesUser.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        mesUser.setAdapter(mAdapter);
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
    public void ajout_user(View view) {
        Intent intent=new Intent(Affiche_user.this,Ajout_user.class);
        startActivity(intent);
    }
    public void loggout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        appSharedPrefs2.edit().clear().commit();
        finish();
        startActivity(intent);
    }
    public void onBackPressed(){
        Intent intent = new Intent(Affiche_user.this, Acceuil.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);
    }
}