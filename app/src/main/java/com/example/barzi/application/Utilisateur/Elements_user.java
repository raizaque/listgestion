package com.example.barzi.application.Utilisateur;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Element;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.barzi.application.Utilisateur.RecyclerItemClickListener.*;

public class Elements_user extends AppCompatActivity {
    private RecyclerView Element;
    private ArrayList<com.example.barzi.application.beans_DAO.Element> mesElements;
    private ElementAdapter eAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_user);
        Element = (android.support.v7.widget.RecyclerView) findViewById(R.id.maListe);
        Intent i=getIntent();
        Bundle bundle = i.getExtras();
        String id_list="";
        if (bundle != null) {
            id_list = bundle.getString("id_liste")+"";
            Log.d("idlise",id_list);

        }
        Toast msge = Toast.makeText(getApplicationContext(), "Votre Liste", LENGTH_LONG);
        msge.show();

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

