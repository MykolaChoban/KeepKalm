package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.io.IOException;

public class MusicActivity extends AppCompatActivity {

    private MediaPlayer soundPlayer = new MediaPlayer();
    private String[] soundResources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        soundResources = listRaw();

        for(String songFileName : soundResources)
            ((LinearLayout) findViewById(R.id.mainLayout)).addView(createSongButton(songFileName));
        }

    public String[] listRaw(){
        String[] files = null;
        try {
            AssetManager assetManager = getAssets();
            files = assetManager.list("music");
        }catch (Exception e) {

        }
        return files;
    }

    public Button createSongButton(final String songName){
        final Button button = new Button(this);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        button.setText(songName.replace(".mp3","").replace("_"," "));

        //FIXME
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        button.setWidth(width);

        //button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPlayer.reset();
                Button button = (Button) v;

                try {
                    AssetFileDescriptor descriptor = v.getContext().getAssets().openFd("music/" + songName);
                    soundPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
                    descriptor.close();
                    soundPlayer.prepare();
                    soundPlayer.setLooping(true);
                    soundPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //soundPlayer = MediaPlayer.create(v.getContext(), getResources().getIdentifier(button.getText().toString(), "assets/music", getPackageName()));
                soundPlayer.start();
            }
        });

        return button;
    }

    public void pause(View view){
        soundPlayer.pause();
    }
}