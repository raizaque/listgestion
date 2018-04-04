package com.example.barzi.application.Administrateur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.barzi.application.MainActivity;
import com.example.barzi.application.R;
import com.example.barzi.application.Utilisateur.Profil_user;

public class Acceuil extends AppCompatActivity {
private ImageButton users;
private ImageButton listes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_admin);
    }

    public void loggout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
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
}
