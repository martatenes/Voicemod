package com.voicemod.codechallenge;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voicemod.codechallenge.adapters.VideoAdapter;
import com.voicemod.codechallenge.utils.MediaUtility;
import com.voicemod.codechallenge.utils.PermissionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int VIDEO_CAPTURE = 101;
    public static final int REQUEST_CAMERA_CODE = 100;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    VideoAdapter mAdapter;
    String fileName;
    List<File> mVideoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        showGalleryVideos();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showGalleryVideos() {
        mVideoList = MediaUtility.fetchVideosFromGallery(this);
        mAdapter.updateData(mVideoList);
    }

    private void initUI() {
        recyclerView = (RecyclerView) findViewById(R.id.rvVideos);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        mAdapter = new VideoAdapter(this, this::OnClickVideo);
        recyclerView.setAdapter(mAdapter);
    }

    private void OnClickVideo(File video) {
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void launchCamera() {

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileName = System.currentTimeMillis() + ".mp4";
        File path = new File(getFilesDir(), "VoiceMod");
        File filePath = new File(path + "/" + fileName);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.TITLE, fileName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.record:

                if (!PermissionUtils.hasPermission(MainActivity.this, Manifest.permission.CAMERA)) {
                    PermissionUtils.managePermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
                }

                else {
                    launchCamera();
                }

                break;

            case R.id.settings:
                //TODO: Lanzar activity de ajustes
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_CODE:
                break;
            default:
                break;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                MediaUtility.SaveMediaFile(this, data.getData(), fileName);
                showGalleryVideos();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}








