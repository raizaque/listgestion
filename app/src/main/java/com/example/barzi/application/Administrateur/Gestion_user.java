package com.example.barzi.application.Administrateur;
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
import com.example.barzi.application.Utilisateur.Affiche_list;
import com.example.barzi.application.Utilisateur.Profil_user;
import com.example.barzi.application.beans_DAO.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class Gestion_user extends AppCompatActivity {
private Spinner spinner_role;
private Spinner spinner_permission;
private Utilisateur user;
private ProgressBar progressBar;
private RelativeLayout relativeLayout;
private EditText pseudo;
private EditText motdepasse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_user);
        spinner_role=(Spinner)findViewById(R.id.spinner_role);
        spinner_permission=(Spinner)findViewById(R.id.spinner_permission);
        String[] role=getResources().getStringArray(R.array.array_Role);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, role);
        spinner_role.setAdapter(adapter);
        String[] permission=getResources().getStringArray(R.array.array_permission);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, permission);
        spinner_permission.setAdapter(adapter2);
        pseudo=(EditText)findViewById(R.id.id_field);
        motdepasse=(EditText) findViewById(R.id.password_field);
        Intent in=getIntent();
        Bundle bundle= in.getExtras();
        user=new Utilisateur();
        if (bundle!=null){
            String id=(String)bundle.get("id_User")+"";
            Log.d("iduser",id);
            user.setId(id);
            recupere_unutilisateur_du_serveur(id);
        }

        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
    }

    private void recupere_unutilisateur_du_serveur(String id) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET,user.getApi_url()+"/"+user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("message");
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    if (jsonarray.length()!=0) {
                        JSONObject liste_obj = jsonarray.getJSONObject(0);
                        user.setId(liste_obj.getString("idUser"));
                        user.setPseudo(liste_obj.getString("pseudo"));
                        user.setPassword(liste_obj.getString("password"));
                        user.setPermission(liste_obj.getString("permission"));
                        user.setRole(liste_obj.getString("role"));
                        pseudo.setText(user.getPseudo());
                        motdepasse.setText(user.getPassword());
                        spinner_role.setSelection(Integer.valueOf(user.getRole()));
                        spinner_permission.setSelection(Integer.valueOf(user.getPermission()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast msge = Toast.makeText(getApplicationContext(), "une erreur c'est produit", LENGTH_LONG);
                msge.show();
            }
        });
        requestQueue.add(request);

    }

    public void modification(View view) {
        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        String url = user.getApi_url() + "/" + user.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast = Toast.makeText(getApplicationContext(), "Modification Effectuer", Toast.LENGTH_LONG);
                toast.show();
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);
                Log.d("mdification", response.toString());
                Intent intent=new Intent(Gestion_user.this,Affiche_user.class);
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
                parameters.put("pseudo", pseudo.getText().toString());
                parameters.put("password", motdepasse.getText().toString());
                parameters.put("permission", (spinner_permission.getSelectedItemId()) + "");
                parameters.put("role", spinner_role.getSelectedItemId()+"");
                return parameters;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(request);
    }
    public void suppression(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.DELETE, user.getApi_url() + "/ " + user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast = Toast.makeText(getApplicationContext(), "Utilisateur supprimerZ", Toast.LENGTH_LONG);
                toast.show();
                Log.d("response", response.toString());
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);
                Intent intent=new Intent(Gestion_user.this,Affiche_user.class);
                overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast msge = Toast.makeText(getApplicationContext(), "Erreur element n'est pas supprimé", LENGTH_LONG);
                msge.show();
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
        Intent intent=new Intent(Gestion_user.this,Affiche_user.class);
        startActivity(intent);
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
        Intent intent= new Intent(Gestion_user.this, Affiche_user.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);
    }
}
