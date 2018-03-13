package com.example.barzi.application.Utilisateur;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.barzi.application.R;
import com.example.barzi.application.inscription;

import java.util.ArrayList;

import static com.example.barzi.application.Utilisateur.RecyclerItemClickListener.*;

public class Profil_user extends AppCompatActivity {

    private Context context;
    private RecyclerView maListe;
    private ArrayList<MaListe> mesListes;
    private ListeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        maListe = (RecyclerView) findViewById(R.id.maListe);


        maListe.addOnItemTouchListener(
                new RecyclerItemClickListener(context, maListe ,new OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent_elt = new Intent(Profil_user.this, Elements_user.class);
                        startActivity(intent_elt);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        mesListes= new ArrayList<>();
        mesListes.add(new MaListe("Course", "achat de ramadhan"));
        mesListes.add(new MaListe("Course", "achat de ramadhan"));
        mesListes.add(new MaListe("Course", "achat de ramadhan"));
        mesListes.add(new MaListe("Course", "achat de ramadhan"));
        mesListes.add(new MaListe("Course", "achat de ramadhan"));
        mesListes.add(new MaListe("Course", "achat de ramadhan"));

        mAdapter = new ListeAdapter(mesListes);
        maListe.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        maListe.setAdapter(mAdapter);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);

        String[] countries=getResources().getStringArray(R.array.array_recherche);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);

        spinner.setAdapter(adapter);
    }
}
