package com.voicemod.codechallenge.video_detail;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.voicemod.codechallenge.R;
import com.voicemod.codechallenge.constants.Constants;
import com.voicemod.codechallenge.model.Video;
import com.voicemod.codechallenge.utils.AlertUtils;

public class VideoDetailActivity extends AppCompatActivity {
    VideoView videoView;
    Video mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        if (getIntent().hasExtra(Constants.EXTRA_VIDEO)) {
            mVideo = (Video) getIntent().getExtras().getSerializable(Constants.EXTRA_VIDEO);
        }
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startVideo();
    }

    private void startVideo(){
        videoView.start();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (mVideo != null) actionBar.setTitle(mVideo.getFile().getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);


        }
    }

    private void initUI(){
        setToolBar();
        videoView =(VideoView)findViewById(R.id.videoView);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView); videoView.setMediaController(mediaController);
        Uri uri = Uri.parse(mVideo.getFile().getAbsolutePath());
        videoView.setVideoURI(uri);
        videoView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_video_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.action_delete){
            AlertUtils.ShowAlertWithCallback(this, getString(R.string.TR_ESTAS_SEGURO_ELIMINAR_VIDEO), new AlertUtils.AlertAcceptCallback(){

                @Override
                public void onClickAccept() {
                    videoView.stopPlayback();
                    if (mVideo.getFile().exists()){
                        if (mVideo.getFile().delete()){ //TODO: Sería mejor que lo hiciese el presenter pero como no hay casi lógica lo dejamos aquí
                            finish();
                        }else{
                            Toast.makeText(VideoDetailActivity.this, getString(R.string.TR_NO_SE_PUDO_ELIMINAR), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}