package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.lang.reflect.Field;

public class MusicActivity extends AppCompatActivity {

    private MediaPlayer soundPlayer = new MediaPlayer();
    private String[] soundResources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        soundResources = listRaw();

        for(String songFileName : soundResources)
            ((LinearLayout) findViewById(R.id.mainLayout)).addView(createSongButton(songFileName));
        }

    public String[] listRaw(){
        Field[] fields = R.raw.class.getDeclaredFields(); //R.raw.class.getFields();
        String[] files = new String[fields.length];
        for(int count=0; count < fields.length; count++){
            files[count] = fields[count].getName();
        }
        return files;
    }

    public Button createSongButton(String songName){
        final Button button = new Button(this);
        button.setText(songName);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        button.setWidth(width);



        //button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.pause();
                Button button = (Button) v;
                soundPlayer = MediaPlayer.create(v.getContext(), getResources().getIdentifier(button.getText().toString(), "raw", getPackageName()));
                soundPlayer.start();
            }
        });

        return button;
    }

    public void pause(View view){
        soundPlayer.pause();
    }
}