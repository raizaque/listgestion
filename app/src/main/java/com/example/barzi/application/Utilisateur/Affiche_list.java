package com.example.barzi.application.Utilisateur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.barzi.application.R;

public class Affiche_list extends AppCompatActivity {
    private TextView titre;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_list);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        titre=(TextView)findViewById(R.id.TitreElement);
        description=(TextView)findViewById(R.id.description);
        String[] visibility=getResources().getStringArray(R.array.array_visibilité);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, visibility);
        spinner.setAdapter(adapter);
    }

    public void modification(View view) {
    }

    public void supprimer(View view) {
    }
}
