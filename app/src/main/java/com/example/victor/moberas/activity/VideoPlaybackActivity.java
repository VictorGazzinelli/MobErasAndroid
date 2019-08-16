package com.example.victor.moberas.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.victor.moberas.R;


public class VideoPlaybackActivity extends AppCompatActivity {

    private final int VIDEO_ID = R.raw.video;
    private final int VIDEO_VIEW_ID = R.id.vv_videoView;

    private final String TAG_RUNTIME = "Tag_Runtime";

    private Uri videoPath;

    private VideoView videoView;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_video_playback);
        videoView = findViewById(VIDEO_VIEW_ID);
        videoPath = getUri(VIDEO_ID);
        videoView.setVideoURI(videoPath);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG_RUNTIME, "Going To MenuActivity...");
                Bundle extras = getIntent().getExtras();
                Intent intent = new Intent(VideoPlaybackActivity.this,MenuActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Keep Screen On
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Block Going Back
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        videoView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        videoView.start();
        super.onResume();
    }

    private Uri getUri(int id){
        return (Uri.parse("android.resource://" + getPackageName() + "/" + id));
    }
}