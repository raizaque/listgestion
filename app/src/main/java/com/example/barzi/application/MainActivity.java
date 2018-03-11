package com.example.barzi.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.barzi.application.Utilisateur.Profil_user;

public class MainActivity extends AppCompatActivity {
    private ImageButton mboutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mboutton= (ImageButton) findViewById(R.id.monBoutton);
    }

    public void hello(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void inscription(View view) {
        Intent intent_loging=new Intent(this,inscription.class);
        startActivity(intent_loging);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        finish();
    }

 public void connexion(View view) {
         Intent intent = new Intent(MainActivity.this, Profil_user.class);
         startActivity(intent);
         overridePendingTransition(R.anim.fadeout,R.anim.fadein);
    }
}
