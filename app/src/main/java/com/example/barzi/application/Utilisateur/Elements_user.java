package com.example.barzi.application.Utilisateur;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.barzi.application.R;

import java.util.ArrayList;

import static com.example.barzi.application.Utilisateur.RecyclerItemClickListener.*;

public class Elements_user extends AppCompatActivity {
    private RecyclerView Element;
    private ArrayList<Element> mesElements;
    private ElementAdapter eAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_user);

        Element = (android.support.v7.widget.RecyclerView) findViewById(R.id.maListe);

        Element.addOnItemTouchListener(
                new RecyclerItemClickListener(context, Element ,new OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Intent intent_list = new Intent(Elements_user.this, Affiche_elt.class);
                        startActivity(intent_list);
                    }
                })
        );

        mesElements= new ArrayList<>();
        mesElements.add(new Element("Course", "achat de ramadhan"));
        mesElements.add(new Element("Course", "achat de ramadhan"));
        mesElements.add(new Element("Course", "achat de ramadhan"));
        mesElements.add(new Element("Course", "achat de ramadhan"));
        mesElements.add(new Element("Course", "achat de ramadhan"));
        mesElements.add(new Element("Course", "achat de ramadhan"));

        eAdapter = new ElementAdapter(mesElements);
        Element.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        Element.setAdapter(eAdapter);

    }
}

