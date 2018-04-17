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
import com.example.barzi.application.beans_DAO.Liste;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class Ajouter_Liste extends AppCompatActivity {
    private EditText titre;
    private EditText description;
    private Spinner spinner;
    private Utilisateur user;
    private TextView textView;
    private Liste liste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__liste);
        ////////////////////////////////////////////
        liste= new Liste();
        spinner=(Spinner)findViewById(R.id.spinner);
        titre=(EditText) findViewById(R.id.Title_edittext) ;
        description=(EditText)findViewById(R.id.Desciption_edit_text);
        /////////////////////////////////////////////////
        String[] visibility=getResources().getStringArray(R.array.array_visibilité);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, visibility);
        spinner.setAdapter(adapter);
        ////////////////////////////////////////
        SharedPreferences appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView=(TextView) findViewById(R.id.textView);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");

        //////////////////////////////////
    }

    public void Ajouter(View view) {
    if((titre.getText().toString().equals("")&&description.toString().equals(""))||!user.getId().equals("0")){
        Toast toast=Toast.makeText(getApplicationContext(),"Un ou plusieur champs sont vide",Toast.LENGTH_LONG);
        toast.show();
    }
    else
    {

        ajouter_liste();
    }
    }

    private void ajouter_liste() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, liste.getApi_url(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseeee",response.toString());
                Toast toast = Toast.makeText(getApplicationContext(), "Liste Ajouter", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(Ajouter_Liste.this, Profil_user.class);
                overridePendingTransition(R.anim.fadeout, R.anim.fadein);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast msge = Toast.makeText(getApplicationContext(), "Erreur element n'est pas Ajouter", LENGTH_LONG);
                msge.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("title",titre.getText().toString()+"");
                parameters.put("description",description.getText().toString()+"");
                parameters.put("visibility",spinner.getSelectedItemPosition()+"");
                parameters.put("idUser",user.getId()+"");
                return parameters;
            }
        };
        requestQueue.add(request);

    }

    public void loggout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        appSharedPrefs2.edit().clear().commit();
        finish();
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){
        Intent intent= new Intent(Ajouter_Liste.this, Profil_user.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);

    }
}
