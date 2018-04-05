package com.example.barzi.application.Utilisateur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barzi.application.MainActivity;
import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Liste;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.example.barzi.application.loggin_bar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class Affiche_list extends AppCompatActivity {
    private EditText titre;
    private EditText description;
    private Utilisateur user;
    private TextView textView;
    private Liste liste;
    private Spinner spinner;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_list);
        liste=new Liste();
        spinner=(Spinner)findViewById(R.id.spinner);
        titre=(EditText) findViewById(R.id.Title_edittext) ;
        description=(EditText)findViewById(R.id.Desciption_edit_text);
        ///////////////////////////////////////
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
/******************* pour le bonjour user ************/
        SharedPreferences appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView=(TextView) findViewById(R.id.textView);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");
        /*********************************************/
        String[] visibility=getResources().getStringArray(R.array.array_visibilité);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, visibility);
        spinner.setAdapter(adapter);
        Intent in=getIntent();
        Bundle bundle= in.getExtras();
        if (bundle!=null){
            String id=(String)bundle.get("id_liste");
            liste.setId(id);
            recupere_unelist_du_serveur(id);
        }
    }
    private void recupere_unelist_du_serveur(String id) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,liste.getApi_url()+"/"+liste.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    if (jsonarray.length()!=0) {
                        JSONObject liste_obj = jsonarray.getJSONObject(0);
                        liste.setId(liste_obj.getString("idliste"));
                        liste.setTitre(liste_obj.getString("title"));
                        liste.setDescription(liste_obj.getString("description"));
                        liste.setVisibilite(liste_obj.getString("visibility"));
                        liste.setIdutilisateur(liste_obj.getString("idUser"));
                        titre.setText(liste.getTitre());
                        description.setText(liste.getDescription());
                        int a= Integer.parseInt(liste.getVisibilite());
                        spinner.setSelection(a);
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
    public void modification(View view) {
        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        if( !titre.getText().toString().equals(liste.getTitre())||    !description.getText().toString().equals(liste.getDescription())) {
            String url = liste.getApi_url() + "/ " + liste.getId();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Modification Effectuer", Toast.LENGTH_LONG);
                    toast.show();
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    Log.d("erreur", response.toString());
                    Intent intent = new Intent(Affiche_list.this, Profil_user.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    //or try with this:
                    //headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    return headers;
                }

                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("title", titre.getText().toString());
                    parameters.put("description", description.getText().toString());
                    parameters.put("visibility", (spinner.getSelectedItemId())+"");
                    parameters.put("idUser", user.getId());
                    return parameters;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            Log.d("err", request.toString());
            requestQueue.add(request);
        }
    }
    public void supprimer(View view) {
        if (user.getId().equals(liste.getIdutilisateur())) {
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.DELETE, liste.getApi_url() + "/ " + liste.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast toast = Toast.makeText(getApplicationContext(), "La Liste est modifier", Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("response",response.toString());
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                   Intent intent = new Intent(Affiche_list.this, Profil_user.class);
                    overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast msge = Toast.makeText(getApplicationContext(), "Erreur element n'est pas supprimé", LENGTH_LONG);
                    msge.show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    //or try with this:
                    //headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    return headers;
                }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            };
            requestQueue.add(request);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Vous n'avez pas le droit", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
