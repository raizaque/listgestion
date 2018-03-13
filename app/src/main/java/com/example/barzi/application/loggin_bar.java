package com.example.barzi.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class loggin_bar extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin_bar);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        progressBar.
                postInvalidate();
        /*Intent intent= new Intent(this,Acceuil.class);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        startActivity(intent);
            */

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, "https://barzizouiyahya.000webhostapp.com/utilisateur.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    JSONArray jsonarray = jsonObject.getJSONArray("ConnectCompte");
                    Log.d("hellooooooooooooooooooooooooo",jsonarray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("method","connect");
                parameters.put("Email","barzizouiyahya@gmail.com");
                parameters.put("PassWord","azerty");
                return parameters;
            }
        };
        requestQueue.add(request);

    }

    public void homme(View view) {
    }
    //inner class to perform network request extending an AsyncTask

}
