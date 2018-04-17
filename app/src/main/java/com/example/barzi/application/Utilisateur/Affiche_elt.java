package com.example.barzi.application.Utilisateur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barzi.application.MainActivity;
import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Element;
import com.example.barzi.application.beans_DAO.Etiquéte;
import com.example.barzi.application.beans_DAO.Liste;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class Affiche_elt extends AppCompatActivity {
    private TextView titre;
    private EditText titre_field;
    private EditText descrip_field;
    static EditText tag_field;
    private Element element;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private Spinner spinner;
    private TextView dateajout;
    private TextView datemodification;
    private Liste liste;
    private TextView textView;
    private Utilisateur user;
    private Etiquéte etiquéte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_elt);
        ///////////////////////////////////////////
        element= new Element();
        liste=new Liste();
        spinner=(Spinner)findViewById(R.id.spinner);
        titre=(TextView)findViewById(R.id.titre_field);
        titre_field=(EditText)findViewById(R.id.titre_field);
        descrip_field=(EditText)findViewById(R.id.description_field);
        tag_field=(EditText)findViewById(R.id.tag_field);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.GONE);
                tag_field.setText(Tags.tags.getText().toString());
            }
        });

        dateajout=(TextView) findViewById(R.id.textView2);
        datemodification=(TextView) findViewById(R.id.date_dmofiication);
        etiquéte=new Etiquéte();
        ///////////////////////////////////////////////
        SharedPreferences appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView=(TextView) findViewById(R.id.textView);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");
        ////////////////////////////////////////////////////////////////
        String[] statut_optionnel=getResources().getStringArray(R.array.Statut_Optionnel);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, statut_optionnel);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        //////////////////////////////////////
        Intent in=getIntent();
        Bundle bundle= in.getExtras();
        if (bundle!=null){
            String id=(String)bundle.get("id_element");
            liste.setIdutilisateur((String)bundle.get("iduserlist"));
            element.setId(id);
            liste.setId((String)bundle.get("id_liste")+"");
            recupere_unElement_du_serveur(id);
            requete_etiquéte_du_serveur();
        }
    }
    private void requete_etiquéte_du_serveur() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,etiquéte.getApi_url_etiquéte()+"/"+element.getId()+"/etiquettes", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String tagee="";
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    if (jsonarray.length()!=0) {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject liste_obj = jsonarray.getJSONObject(0);
                            ///////////////////////////////////////////////////////////////
                            Log.d("tag", liste_obj.toString());
                            etiquéte.setIdetiquette(liste_obj.getString("idetiquette"));
                            etiquéte.setTag(liste_obj.getString("tag"));
                            etiquéte.setIdUser(liste_obj.getString("idUser"));
                            tagee+=" #"+etiquéte.getTag();
                        }
                        tag_field.setText(tagee);
                    ////////////////////////////////////////////////////////////////////
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
    private void recupere_unElement_du_serveur(String id) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,element.getApi_url2()+"/"+element.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    if (jsonarray.length()!=0) {
                        JSONObject liste_obj = jsonarray.getJSONObject(0);
                        ///////////////////////////////////////////////////////////////
                        element.setId(liste_obj.getString("idelements"));
                        element.setDate_creation(liste_obj.getString("date_creation"));
                        element.setDateDerniere_modif(liste_obj.getString("date_modif"));
                        element.setTitre_element(liste_obj.getString("titre"));
                        element.setDescription_element(liste_obj.getString("description"));
                        element.setStatut_optionnel(liste_obj.getString("statut"));
                        element.setIdliste(liste_obj.getString("idListe"));
                        //////////////////////////////////////////////////////////////////////
                        titre.setText(element.getTitre_element());
                        descrip_field.setText(element.getDescription_element());
                        dateajout.setText(dateajout.getText().toString()+" "+element.getDate_creation());
                        datemodification.setText(datemodification.getText().toString()+" "+element.getDateDerniere_modif());
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
    public void suppprimé(View view) {
        if (user.getId().equals(liste.getIdutilisateur()) || user.getRole().equals("0") || !user.getId().equals("0")) {
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            liste = new Liste();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.GET, liste.getApi_url() + "/" + element.getIdliste(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray jsonarray = jsonObject.getJSONArray("message");
                        if (jsonarray.length() != 0) {
                            JSONObject liste_obj = jsonarray.getJSONObject(0);
                            liste.setId(liste_obj.getString("idliste"));
                            liste.setTitre(liste_obj.getString("title"));
                            liste.setDescription(liste_obj.getString("description"));
                            liste.setVisibilite(liste_obj.getString("visibility"));
                            liste.setIdutilisateur(liste_obj.getString("idUser"));
                            titre.setText(liste.getTitre());
                            if (liste.getIdutilisateur().equals(user.getId())) {
                                Log.d("supprseion", " debut fuction detele");
                                delete_lelemment();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Vous n'avez pas le droit", Toast.LENGTH_LONG);
                                toast.show();
                            }
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
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Vous n'avez pas le droit", Toast.LENGTH_LONG);
            toast.show();

        }
    }
    private void delete_lelemment() {
        if (user.getId().equals(liste.getIdutilisateur()) || user.getRole().equals("0") || !user.getId().equals("0")) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.DELETE, liste.getApi_url() + "/ " + element.getIdliste(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast toast = Toast.makeText(getApplicationContext(), "La Liste est SUPRIMER", Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("erreur", response.toString() + "+" + user.getId());
                    Log.d("response", response.toString());
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    Intent intent = new Intent(Affiche_elt.this, Elements_user.class);
                    intent.putExtra("id_liste",liste.getId());
                    overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast msge = Toast.makeText(getApplicationContext(), "Erreur element n'est pas supprimé", LENGTH_LONG);
                    msge.show();
                    Intent intent = new Intent(Affiche_elt.this, Elements_user.class);
                    intent.putExtra("id_liste",liste.getId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadeout, R.anim.fadein);
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
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            requestQueue.add(request);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Vous n'avez pas le droit", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void modfier(View view) {
        if (user.getId().equals(liste.getIdutilisateur()) || user.getRole().equals("0") || !user.getId().equals("0")) {
            Date date = new Date();
            final String dte = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
            progressBar.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            if (!titre.getText().toString().equals(element.getTitre_element()) || !descrip_field.getText().toString().equals(element.getDescription_element())) {
                String url = element.getApi_url2() + "/ " + element.getId();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Modification Effectuer", Toast.LENGTH_LONG);
                        toast.show();
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                        Log.d("response", response.toString());
                        Intent intent = new Intent(Affiche_elt.this, Elements_user.class);
                        intent.putExtra("id_liste",element.getIdliste());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Affiche_elt.this,"Error",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Affiche_elt.this, Elements_user.class);
                        intent.putExtra("id_liste",element.getIdliste());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
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
                        parameters.put("date_creation", element.getDate_creation() + "");
                        parameters.put("date_modif", dte + "");
                        parameters.put("titre", titre.getText().toString() + "");
                        parameters.put("description", descrip_field.getText().toString() + "");
                        parameters.put("statut", (spinner.getSelectedItemId() + ""));
                        parameters.put("idListe", element.getIdliste() + "");
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
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Vous n'avez pas le droit", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void loggout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        appSharedPrefs2.edit().clear().commit();
        finish();
        startActivity(intent);
    }
    public void onBackPressed(){
        Intent intent = new Intent(Affiche_elt.this, Elements_user.class);
        intent.putExtra("id_liste",element.getIdliste());
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);

    }
    public void geretag(View view) {
        relativeLayout.setVisibility(View.VISIBLE);
        Tags fragment = new Tags();
        Bundle bundle = new Bundle();
        bundle.putString("edttext",tag_field.getText().toString());
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.blackenScreen,fragment,fragment.getClass().getSimpleName())
                .addToBackStack(null).commit();
        relativeLayout.bringToFront();
    }
}
