package com.example.barzi.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.barzi.application.Administrateur.Acceuil;

public class loggin_bar extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin_bar);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        progressBar.
                postInvalidate();
        Intent intent= new Intent(this,Acceuil.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);

    }

    public void homme(View view) {
    }
}
