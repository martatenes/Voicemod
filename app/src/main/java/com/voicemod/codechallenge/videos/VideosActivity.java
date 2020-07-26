package com.voicemod.codechallenge.videos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.voicemod.codechallenge.R;
import com.voicemod.codechallenge.adapters.VideoAdapter;
import com.voicemod.codechallenge.constants.Constants;
import com.voicemod.codechallenge.model.Video;
import com.voicemod.codechallenge.settings.SettingsActivity;
import com.voicemod.codechallenge.utils.AlertUtils;
import com.voicemod.codechallenge.utils.PermissionUtils;
import com.voicemod.codechallenge.utils.SharedPrefUtils;
import com.voicemod.codechallenge.video_detail.VideoDetailActivity;

import java.util.List;

public class VideosActivity extends AppCompatActivity implements VideosContract.View{

    VideosPresenter mPresenter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    VideoAdapter mAdapter;
    ProgressBar progressBar;
    TextView tvEmpty;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefUtils.isFirstRun(this)){
            SharedPrefUtils.setDefaultPreferences(this);
        }
        initUI();
        setUpListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null){
            mPresenter = new VideosPresenter(this);
        }
        mPresenter.retrieveVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }



    private void initUI() {
        tvEmpty = findViewById(R.id.tvEmpty);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout =  findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.rvVideos);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        mAdapter = new VideoAdapter(this, this::onClickVideo);
        recyclerView.setAdapter(mAdapter);

    }

    public void onClickVideo (Video video){
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra(Constants.EXTRA_VIDEO, video);
        startActivity(intent);
    }

    private void setUpListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.retrieveVideos());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.record:

                if (!PermissionUtils.hasPermission(VideosActivity.this, Manifest.permission.CAMERA)) {
                    PermissionUtils.managePermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, Constants.REQUEST_CAMERA_CODE);
                }
                else {
                    fileName = System.currentTimeMillis() + ".mp4";
                    mPresenter.prepareCamera(fileName);
                }

                break;

            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_CAMERA_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fileName = System.currentTimeMillis() + ".mp4";
                mPresenter.prepareCamera(fileName);
            }
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                mPresenter.saveVideo(data.getData(), fileName);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        AlertUtils.ShowSimpleAlert(this, error);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshVideos(List<Video> videos) {
        tvEmpty.setVisibility(videos.size() > 0 ? View.GONE : View.VISIBLE);
        mAdapter.updateData(videos);
    }

    @Override
    public void launchCamera(Intent intent) {
        startActivityForResult(intent, Constants.VIDEO_CAPTURE);
    }
}








