package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,
        YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener {

    YouTubePlayerView playerView;
    String apiKey = "AIzaSyD_7Vc-sZL0t1b2OxO4qAwAA4weksiOYWc";
    String videoID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoID = getRandomVideo();
        playerView = findViewById(R.id.play_video);
        playerView.initialize(apiKey, this);
    }


    public String getRandomVideo() {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.videos));
        scanner.nextLine();
        ArrayList<String> videoIDList = new ArrayList<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            videoIDList.add(line.trim());
        }
        Random random = new Random();
        int randIndex = random.nextInt(videoIDList.size());
        return videoIDList.get(randIndex);
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);
        if(!b) youTubePlayer.cueVideo(getRandomVideo());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
    }
}