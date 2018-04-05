package com.example.barzi.application.Administrateur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.barzi.application.R;

public class Gestion_user extends AppCompatActivity {
private Spinner spinner_role;
private Spinner spinner_permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_user);
        spinner_role=(Spinner)findViewById(R.id.spinner_role);
        spinner_permission=(Spinner)findViewById(R.id.spinner_permission);
        String[] role=getResources().getStringArray(R.array.array_Role);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, role);
        spinner_role.setAdapter(adapter);
        spinner_role.setSelection(0);

        String[] permission=getResources().getStringArray(R.array.array_permission);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, permission);
        spinner_permission.setAdapter(adapter2);
        spinner_permission.setSelection(0);

    }

    public void modification(View view) {
        Intent intent=new Intent(Gestion_user.this,Affiche_user.class);
        startActivity(intent);
    }

    public void suppression(View view) {
        Intent intent=new Intent(Gestion_user.this,Affiche_user.class);
        startActivity(intent);
    }
}
