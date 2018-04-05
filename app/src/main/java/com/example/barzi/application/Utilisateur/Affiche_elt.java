package com.example.barzi.application.Utilisateur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.barzi.application.R;

public class Affiche_elt extends AppCompatActivity {
    private TextView titre;
    private TextView description;
    private TextView tag;
    private EditText titre_field;
    private EditText descrip_field;
    private EditText tag_field;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_elt);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        titre=(TextView)findViewById(R.id.Titre_element);
        description=(TextView)findViewById(R.id.description_ajout_element);
        tag=(TextView)findViewById(R.id.Tag);
        titre_field=(EditText)findViewById(R.id.titre_field);
        descrip_field=(EditText)findViewById(R.id.description_field);
        tag_field=(EditText)findViewById(R.id.tag_field);
        String[] statut_optionnel=getResources().getStringArray(R.array.Statut_Optionnel);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, statut_optionnel);
        spinner.setAdapter(adapter);
    }
}
