package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SharedPreferences prefs;
    private Button musicButton, decisionButton, catButton, aboutYouButton, randomButton, trumpButton;

    String user_full_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("userInfo",MODE_PRIVATE);
        user_full_name = prefs.getString("user_full_name","");

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

    public void launchTrump(View view) {
        Intent intent = new Intent(this, TrumpActivity.class);
        startActivity(intent);
    }

    public void launchMusic(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    public void launchAboutYou(View view) {
        Intent intent = new Intent(this, AboutYouActivity.class);
        startActivity(intent);
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