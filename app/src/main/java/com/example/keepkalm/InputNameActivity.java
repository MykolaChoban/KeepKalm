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
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    validateName();
                    return true;
                }
                return false;
            }
        });

        mainActivityIntent = new Intent(this, MainActivity.class);
    }

    public void validateName() {
        if (!editText.getText().toString().matches("")){
            //save the full name in sharedPreference
            prefs = getSharedPreferences("userInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_full_name",editText.getText().toString());
            editor.apply();
            startActivity(mainActivityIntent);
        }else{
            //animation: https://github.com/daimajia/AndroidViewAnimations
            YoYo.with(Techniques.Shake).duration(200).repeat(5).playOn(editText);
            Toast.makeText(this,"Don't skip me... I do it for you!",Toast.LENGTH_SHORT).show();
        }
    }
}