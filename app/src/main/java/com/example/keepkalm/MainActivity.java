package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SharedPreferences prefs;
    private TextView name;
    private TextView quoteTextView;
    private Button musicButton, decisionButton, catButton, aboutYouButton, randomButton, trumpButton;

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

        int trackId = getResources().getIdentifier("fart_sound_effect", "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, trackId);

        fetchQuoteOfTheDay(quoteTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            mediaPlayer.start();
        }
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
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "An error occurred: "+error);
            }
        });
        queue.add(request);
    }

    public void launchTrump(View view) {
        //Intent intent = new Intent(this, TrumpActivity.class);
        //startActivity(intent);

        DialogTrump dialog = new DialogTrump();
        dialog.show(getSupportFragmentManager(),"trumpDialog");
    }

    public void launchMusic(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    public void launchAboutYou(View view) {
        //Intent intent = new Intent(this, AboutYouActivity.class);
        //startActivity(intent);

        DialogChuckNorrisJoke dialog = new DialogChuckNorrisJoke();
        dialog.show(getSupportFragmentManager(),"chuckNorrisDialog");
    }

    public void launchCat(View view) {
        Intent intent = new Intent(this, CatActivity.class);
        startActivity(intent);
    }

    public void launchRandom(View view) {
        Intent intent = new Intent(this, RandomActivity.class);
        startActivity(intent);
    }

    public void launchDecision(View view) {
        Intent intent = new Intent(this, DecisionActivity.class);
        startActivity(intent);
    }
}