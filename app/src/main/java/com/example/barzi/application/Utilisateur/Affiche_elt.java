package com.example.barzi.application.Utilisateur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Element;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_LONG;

public class Affiche_elt extends AppCompatActivity {
    private TextView titre;
    private EditText titre_field;
    private EditText descrip_field;
    private EditText tag_field;
    private Element element;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_elt);
        ///////////////////////////////////////////
        element= new Element();
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        titre=(TextView)findViewById(R.id.titre_field);
        titre_field=(EditText)findViewById(R.id.titre_field);
        descrip_field=(EditText)findViewById(R.id.description_field);
        tag_field=(EditText)findViewById(R.id.tag_field);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
        ///////////////////////////////////////////////
        String[] statut_optionnel=getResources().getStringArray(R.array.Statut_Optionnel);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, statut_optionnel);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        //////////////////////////////////////
        Intent in=getIntent();
        Bundle bundle= in.getExtras();
        if (bundle!=null){
            String id=(String)bundle.get("id_element");
            element.setId(id);
            recupere_unElement_du_serveur(id);
        }

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
                        /////////////////////////////////////////////////////////////
                        titre.setText(element.getTitre_element());
                        descrip_field.setText(element.getDescription_element());

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
}
