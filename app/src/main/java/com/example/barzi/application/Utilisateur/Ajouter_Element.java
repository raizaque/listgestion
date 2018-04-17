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
import com.example.barzi.application.beans_DAO.Element;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
public class Ajouter_Element extends AppCompatActivity {
    private EditText titre;
    private EditText description;
    private Spinner spinner;
    private Utilisateur user;
    private TextView textView;
    private Element element;
    private String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__element);
        ///////////////////////////////////////////
        Intent i=getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            id = bundle.getString("id_element")+"";
            }
        ////////////////////////////////////////////
        element= new Element();
        spinner=(Spinner)findViewById(R.id.spinner);
        titre=(EditText) findViewById(R.id.titre_ajout_element) ;
        description=(EditText)findViewById(R.id.description_ajout_element);
        /////////////////////////////////////////////////
        String[] visibility=getResources().getStringArray(R.array.Statut_Optionnel);
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
        //////////////////////////////////////
        Date date=new Date();
        final String dte= date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
        /////////////////////////////
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, element.getApi_url2(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseeee",response.toString());
                Toast toast = Toast.makeText(getApplicationContext(), "Element Ajouter", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(Ajouter_Element.this, Profil_user.class);
                intent.putExtra("id_liste",id);
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
                parameters.put("date_creation","2017-5-2 15:20:10");
                parameters.put("date_modif","2017-5-2 15:20:10");
                parameters.put("titre",titre.getText().toString()+"");
                parameters.put("description",description.getText().toString()+"");
                parameters.put("statut",spinner.getSelectedItemPosition()+"");
                parameters.put("idListe",id+"");
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
    public void onBackPressed(){
        Intent intent = new Intent(Ajouter_Element.this, Elements_user.class);
        intent.putExtra("id_liste",id);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);
    }
}
