package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;

/*TODO:
- add the a function to call the meme API and uptade the image
- maybe use resultActivity than current way
*/
public class FirstActivity extends AppCompatActivity {
    private final int SPLASH_SCREEN_TIMEOUT = 1000;
    private SharedPreferences prefs;
    private String user_full_name;

    @BindView(R.id.motto)
    TextView motto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//to hide the appTheme: https://stackoverflow.com/questions/26492522/how-do-i-remove-the-title-bar-in-android-studio
        setContentView(R.layout.activity_first);

        ButterKnife.bind(this);

        prefs = getSharedPreferences("userInfo",MODE_PRIVATE);
        user_full_name = prefs.getString("user_full_name","");

        //animation: https://github.com/daimajia/AndroidViewAnimations
        YoYo.with(Techniques.FadeInRight).duration(SPLASH_SCREEN_TIMEOUT).repeat(0).playOn(motto);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //if userName isn't present in sharedPreference we have to ask for it
                if(user_full_name.equals("")){
                    DialogName dialogName = new DialogName();
                    dialogName.setCancelable(false); // dialog.setCanceledOnTouchOutside(false); //https://stackoverflow.com/questions/12102777/prevent-android-activity-dialog-from-closing-on-outside-touch/27512501
                    dialogName.show(getSupportFragmentManager(),"nameDialog");
                }else{
                    Intent mainActivityIntent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}