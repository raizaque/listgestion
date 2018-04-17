package com.example.barzi.application.Administrateur;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.example.barzi.application.Utilisateur.Ajouter_Element;
import com.example.barzi.application.Utilisateur.Ajouter_Liste;
import com.example.barzi.application.Utilisateur.Elements_user;
import com.example.barzi.application.Utilisateur.Profil_user;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.example.barzi.application.inscription;
import com.google.gson.Gson;

import static android.widget.Toast.LENGTH_LONG;
public class Ajout_user extends AppCompatActivity {
    private Spinner spinner_role;
    private Spinner spinner_permission;
    private EditText pseudo;
    private EditText motdepasse;
    private Utilisateur user;
    private RelativeLayout relativeLayout;
    private TextView textView;
    private SharedPreferences appSharedPrefs2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_user);
        //////////////////////////////////////////////////////

        textView=(TextView) findViewById(R.id.textView);
        appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");
        ///////////////////////////////////////////////////
        spinner_role=(Spinner)findViewById(R.id.spinner_role);
        spinner_permission=(Spinner)findViewById(R.id.spinner_permission);
        String[] role=getResources().getStringArray(R.array.array_Role);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, role);
        spinner_role.setAdapter(adapter);
        spinner_role.setSelection(0);
        String[] permission=getResources().getStringArray(R.array.array_permission);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, permission);
        spinner_permission.setAdapter(adapter2);
        spinner_permission.setSelection(0);
        pseudo=(EditText)findViewById(R.id.id_field);
        motdepasse=(EditText) findViewById(R.id.password_field);
    }
    public void Ajouter(View view) {
        Utilisateur utilisateur=new Utilisateur(pseudo.getText().toString(),
                motdepasse.getText().toString(),String.valueOf(spinner_permission.
                getSelectedItemPosition()),String.valueOf(spinner_role.
                getSelectedItemPosition()));
        Intent intent=new Intent(Ajout_user.this,Affiche_user.class);
        insertion_donnee_utilisateur(utilisateur);
        startActivity(intent);
    }
    private void insertion_donnee_utilisateur(Utilisateur utilisateur) {
        if (pseudo.getText().toString().equals("") && motdepasse.getText().toString().equals("")) {
            Toast msg = Toast.makeText(getApplicationContext(), "Un des champs est vide", Toast.LENGTH_LONG);
            msg.show();
        } else {
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST,utilisateur.getApi_url()+
                    "?pseudo="+utilisateur.getPseudo()+"&password="+utilisateur.getPassword()
                    +"&permission="+utilisateur.getPermission()+
                    "&role="+utilisateur.getRole(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String reponses=response.toString();
                    Toast msg = Toast.makeText(getApplicationContext(), reponses, LENGTH_LONG);
                    msg.show();
                    Intent intent= new Intent(Ajout_user.this, Affiche_user.class);
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
        Intent intent= new Intent(Ajout_user.this, Affiche_user.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);
    }
}
