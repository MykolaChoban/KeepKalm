package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private TextView quoteTextView;
    private String user_full_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("userInfo",MODE_PRIVATE);
        user_full_name = prefs.getString("user_full_name","");
        TextView name = findViewById(R.id.user_full_name_EditText);
        quoteTextView = findViewById(R.id.quote_TextView);

        name.setText("Hello " + user_full_name);
        //for debug purpose
        Log.i("user name",user_full_name);

        fetchQuoteOfTheDay(quoteTextView);
    }

    public void fetchQuoteOfTheDay(final TextView textView){
        //documentation  http://quotes.rest/
        String url = "https://quotes.rest/qod";
        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //{"success":{"total":1},"contents":{"quotes":[{"quote":"A successful man is one who can lay a firm foundation with the bricks that others throw at him.","length":"95","author":"Sidney Greenberg","tags":["inspire","life","success"],"category":"inspire","language":"en","date":"2020-12-02","permalink":"https:\/\/theysaidso.com\/quote\/sidney-greenberg-a-successful-man-is-one-who-can-lay-a-firm-foundation-with-the","id":"O8OiauUuV2FEq8DZElUNwQeF","background":"https:\/\/theysaidso.com\/img\/qod\/qod-inspire.jpg","title":"Inspiring Quote of the day"}]},"baseurl":"https:\/\/theysaidso.com","copyright":{"year":2022,"url":"https:\/\/theysaidso.com"}}
                        Integer FIRST_ELEMENT = 0;
                        try {
                            JSONObject contents = response.getJSONObject("contents");
                            JSONArray quotes = contents.getJSONArray("quotes");
                            JSONObject quote = (JSONObject) quotes.get(FIRST_ELEMENT);
                            Log.d("Volley",quote.toString());
                            textView.setText(quote.getString("quote") +"\n-"+quote.getString("author"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("debug",e.toString());
            }
        });
        queue.add(request);
    }

    public void launchTrump(View view) {
        DialogTrump dialog = new DialogTrump();
        dialog.show(getSupportFragmentManager(),"trumpDialog");
    }

    public void launchMusic(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    public void launchAboutYou(View view) {
        DialogChuckNorrisJoke dialog = new DialogChuckNorrisJoke();
        dialog.show(getSupportFragmentManager(),"chuckNorrisDialog");
    }

    public void launchCat(View view) {
        Intent intent = new Intent(this, CatActivity.class);
        startActivity(intent);
    }

    public void launchRandom(View view) {
        DialogRandom dialog = new DialogRandom();
        dialog.show(getSupportFragmentManager(),"randomDialog");
    }

    public void launchDecision(View view) {
        DialogDecision dialog = new DialogDecision();
        dialog.show(getSupportFragmentManager(),"decisionDialog");
    }

    public void launchMeme(View view) {
        DialogMeme dialog = new DialogMeme();
        dialog.show(getSupportFragmentManager(),"memeDialog");
    }

    public void launchVideo(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

}