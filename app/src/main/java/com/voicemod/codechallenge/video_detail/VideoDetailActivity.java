package com.voicemod.codechallenge.video_detail;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.voicemod.codechallenge.R;
import com.voicemod.codechallenge.constants.Constants;
import com.voicemod.codechallenge.model.Video;

public class VideoDetailActivity extends AppCompatActivity {

    Video mVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        if (getIntent().hasExtra(Constants.EXTRA_VIDEO)) {
            mVideo = (Video) getIntent().getExtras().getSerializable(Constants.EXTRA_VIDEO);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_video_detail, menu);
        return true;
    }
}