package com.example.barzi.application.Utilisateur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.barzi.application.R;

public class Affiche_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_list);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);

        String[] visibility=getResources().getStringArray(R.array.array_visibilité);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, visibility);

        spinner.setAdapter(adapter);
    }
}
