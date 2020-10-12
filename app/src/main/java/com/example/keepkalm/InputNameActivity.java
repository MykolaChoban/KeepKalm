package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class InputNameActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private EditText editText;
    private Intent mainActivityIntent;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        Log.d("Debug message", "OnCreate");

        editText = findViewById(R.id.edit_person_name);
        mainActivityIntent = new Intent(this,MainActivity.class);

        int trackId = getResources().getIdentifier("and_his_name_is_john_cena_sound_effect", "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, trackId);

    }

    public void validateName(final View view) {
        if (!editText.getText().toString().matches("")){
            //save the full name in sharedPreference
            prefs = getSharedPreferences("userInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_full_name",editText.getText().toString());
            editor.apply();

            mediaPlayer.start();
            editText.setText("JOHN CENA !!!");
            editText.setEnabled(false);
            editText.setTextColor(Color.BLUE);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            findViewById(R.id.welcome_TextView).setVisibility(View.GONE);
            findViewById(R.id.validate_person_name_button).setVisibility(View.GONE);

            //animation: https://github.com/daimajia/AndroidViewAnimations
            YoYo.with(Techniques.RotateIn).duration(1000).repeat(0).playOn(editText);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
                public void run(){
                    YoYo.with(Techniques.FlipOutY).duration(500).repeat(4).playOn(editText);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
                        public void run(){
                            YoYo.with(Techniques.BounceIn).duration(1000).repeat(4).playOn(editText);
                            startActivity(mainActivityIntent);
                        }
                    }, 500*5);
                }
            }, 1000);

        }else{
            YoYo.with(Techniques.Shake).duration(200).repeat(5).playOn(editText);
            Toast.makeText(this,"Don't skip me... I do it for you!",Toast.LENGTH_SHORT).show();
        }

    }
}