package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MusicActivity extends AppCompatActivity {

    private MediaPlayer soundPlayer [] = new MediaPlayer[1];
    private int soundResources [] = {R.raw.nature_of_life};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        for (int i = 0; i < soundPlayer.length; i++){
            soundPlayer[i] = MediaPlayer.create(this, soundResources[i]);
        }
    }

    public void playMusic(View view){

        Button button = findViewById(view.getId());
        String music = button.getText().toString();

        switch (music){
            case "Nature Of Life":
                soundPlayer[0].start();
                break;

            default:
                break;
        }
    }
}