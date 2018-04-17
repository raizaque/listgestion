package com.example.barzi.application.Administrateur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.barzi.application.MainActivity;
import com.example.barzi.application.R;
import com.example.barzi.application.Utilisateur.Profil_user;
import com.example.barzi.application.beans_DAO.Utilisateur;
import com.google.gson.Gson;

public class Acceuil extends AppCompatActivity {
    private ImageButton users;
    private ImageButton listes;
    private Utilisateur user;
    private TextView textView;
    private SharedPreferences appSharedPrefs2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_admin);

        appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs2.getString("user", "");
        user = gson.fromJson(json, Utilisateur.class);
        textView=(TextView) findViewById(R.id.textView);
        textView.setText("    Bonjour "+user.getPseudo()+"    ");

        //////////////////////////////////
    }


    public void loggout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
         appSharedPrefs2 = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        appSharedPrefs2.edit().clear().commit();
        finish();
        startActivity(intent);
    }

    public void Affich_listes(View view) {
        Intent intent = new Intent(this, Profil_user.class);
        finish();
        startActivity(intent);
    }

    public void Affich_users(View view) {
        Intent intent = new Intent(this, Affiche_user.class);
        finish();
        startActivity(intent);
    }
    public void onBackPressed(){}

}
