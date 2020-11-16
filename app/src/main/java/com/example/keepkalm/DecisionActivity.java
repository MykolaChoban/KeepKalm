package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class DecisionActivity extends AppCompatActivity {

    public static RequestQueue requestQueue;
    public static String decisionMakerApiImage = null;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);

        requestQueue = Volley.newRequestQueue(this);
        imageView = findViewById(R.id.gif_here);

        getDecisionGIF("https://yesno.wtf/api/");

    }

     public void loadImage(String url){
        Glide
                .with(this)
                .load(url)
                .into(imageView);
    }

    public String getDecisionGIF(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    decisionMakerApiImage =  response.getString("image");
                    loadImage( response.getString("image"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("API ERROR", "Could not fetch the API data");
            }
        });
        requestQueue.add(jsonObjectRequest);

        return decisionMakerApiImage;
    }

    public void closeDialogDecisionMaker(View view) {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}