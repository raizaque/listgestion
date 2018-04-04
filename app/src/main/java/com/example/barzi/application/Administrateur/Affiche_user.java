package com.example.barzi.application.Administrateur;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Utilisateur;

import java.util.ArrayList;

public class Affiche_user extends AppCompatActivity {
    private Utilisateur user;
    private Context context;
    private RecyclerView mesUser;
    private ArrayList<Utilisateur> Users;
    private UserAdapter mAdapter;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_user);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.postInvalidate();
        relativeLayout=(RelativeLayout)findViewById(R.id.blackenScreen);
        user=new Utilisateur();
        mesUser = (RecyclerView) findViewById(R.id.muser);
        /* user.addOnItemTouchListener(
                new RecyclerItemClickListener(context, user ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast msg = Toast.makeText(getApplicationContext(), "Bienvenu "+mAdapter.get_list_numero(position).getTitre(), Toast.LENGTH_LONG);
                        msg.show();

                        Intent intent_elt = new Intent(Profil_user.this, Elements_user.class);
                        startActivity(intent_elt);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Toast msg = Toast.makeText(getApplicationContext(), "Bienvenu hurrdurr", Toast.LENGTH_LONG);
                        msg.show();
                        Intent intent_list = new Intent(Profil_user.this, Affiche_list.class);
                        startActivity(intent_list);
                    }
                })
        ); */
        Users= new ArrayList<Utilisateur>();
        Users.add(new Utilisateur("01", "Yahia"));

        mAdapter = new UserAdapter(Users);
        mesUser.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mesUser.setAdapter(mAdapter);
        /* Spinner spinner=(Spinner)findViewById(R.id.spinner);
        String[] countries=getResources().getStringArray(R.array.array_recherche);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);
        spinner.setAdapter(adapter);
        */
    }

}