package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatActivity extends AppCompatActivity {

    @BindView(R.id.cat_imageView)
    ImageView catImageView;

    @BindView(R.id.cat_fact_TextView)
    TextView catFactTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        ButterKnife.bind(this);
        setCatImageAndQuote(null);
    }

    public void setCatImageAndQuote(View view){
        fetchCatImage(catImageView);
        fetchCatFact(catFactTextView);
    }

    public void fetchCatImage(ImageView imageView){
        Picasso.get().invalidate("https://cataas.com/cat");//https://github.com/square/picasso/issues/1186
        Picasso.get()
                .load("https://cataas.com/cat")
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE) //.placeholder(R.drawable.default_image_placeholder)
                .into(imageView);
    }

    public void fetchCatFact(final TextView textView){
        //documentation  https://github.com/tonytran1/Cat-Fact-API
        // nul: https://catfact.ninja/     https://cat-fact.herokuapp.com/#/       https://alexwohlbruck.github.io/cat-facts/docs/
        //String url = "https://catfact.ninja/fact";
        String url = "https://the-cat-fact.herokuapp.com/api/randomfact";
        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //{"status":"success","data":[{"fact":"Approximately 40,000 people are bitten by cats in the U.S. annually."}],"message":"Retrieved Single Fact"}
                        Integer FIRST_ELEMENT = 0;
                        try {
                            JSONArray data = response.getJSONArray("data");
                            textView.setText(data.getJSONObject(FIRST_ELEMENT).getString("fact"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "An error occurred.");
            }
        });
        queue.add(request);
    }

}