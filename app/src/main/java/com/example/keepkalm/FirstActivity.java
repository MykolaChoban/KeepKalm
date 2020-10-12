package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
/*TO DO:
- add the a function to call the meme API and uptade the image
- maybe use resultActivity than current way
*/
public class FirstActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String user_full_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        prefs = getSharedPreferences("userInfo",MODE_PRIVATE);
        user_full_name = prefs.getString("user_full_name","");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //if userName isn't present in sharedPreference we have to ask for it
                if(user_full_name.equals("")){
                    Intent inputNameActivityIntent = new Intent(FirstActivity.this, InputNameActivity.class);
                    startActivity(inputNameActivityIntent);
                }else{
                    Intent mainActivityIntent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                }
            }
        }, 2500);
    }
}