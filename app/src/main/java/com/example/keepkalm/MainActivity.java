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

import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;


public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SharedPreferences prefs;
    private Button musicButton, decisionButton, catButton, aboutYouButton, randomButton, trumpButton;
    private ImageView memeImageView;
    String user_full_name, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("userInfo",MODE_PRIVATE);
        user_full_name = prefs.getString("user_full_name","");
        memeImageView = findViewById(R.id.imageView);
        TextView name = findViewById(R.id.user_full_name_EditText);
        name.setText("Hello " + user_full_name);
        //for debug purpose
        Log.i("user name",user_full_name);

        int trackId = getResources().getIdentifier("fart_sound_effect", "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, trackId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            mediaPlayer.start();
        }
    }

    public void getMeme(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //{"postLink":"https://redd.it/k4tjck","subreddit":"dankmemes","title":"humanity","url":"https://i.redd.it/opipssjjzm261.png","nsfw":false,"spoiler":false,"author":"islmakaha","ups":1550,"preview":["https://preview.redd.it/opipssjjzm261.png?width=108\u0026crop=smart\u0026auto=webp\u0026s=5426704f36ec8a58ea14d5bdb2f3bef1d345ff1c","https://preview.redd.it/opipssjjzm261.png?width=216\u0026crop=smart\u0026auto=webp\u0026s=88437148c3af6e1a642e9813d8cdd842e087e749","https://preview.redd.it/opipssjjzm261.png?width=320\u0026crop=smart\u0026auto=webp\u0026s=534d6608d7795f07ebec468fdfda76056714c730"]}
                            JSONObject JSONResponse = new JSONObject(response);
                            String imageUrl = JSONResponse.getString("url");
                            displayMeme(memeImageView, imageUrl);
                        }
                        catch(Exception e){
                            Log.i("debug", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("debug",e.toString());
            }
        });
        queue.add(stringRequest);
    }

    public void displayMeme(ImageView imageView, String url){
        Log.i("debug","url : " + url);
        Picasso.get().invalidate(url);//https://github.com/square/picasso/issues/1186
        Picasso.get()
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE) //.placeholder(R.drawable.default_image_placeholder)
                .into(imageView);
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

    public void launchMeme(View view) {
        DialogMeme dialog = new DialogMeme();
        dialog.show(getSupportFragmentManager(),"memeDialog");
    }
}