package com.voicemod.codechallenge.videos;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.util.Log;

import com.voicemod.codechallenge.App;
import com.voicemod.codechallenge.model.Video;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainModel implements MainContract.Model {

    private final String TAG = "MainModel";

    @Override
    public void getVideos(onRetrieveVideosListener listener) {

        try {
            ArrayList<Video> fileList = new ArrayList<Video>();

            ContextWrapper cw = new ContextWrapper(App.getContext());
            File directory = cw.getDir("VoiceMod", Context.MODE_PRIVATE);
            File[] listFile = directory.listFiles();

            if (listFile != null && listFile.length > 0) {

                for (File aListFile : listFile) {
                    if (!aListFile.isFile()) continue;
                    fileList.add(new Video(aListFile));

                }
            }
            listener.onRetrieveVideosSuccess(fileList);
        }
        catch(Exception ex){
            if (ex.getMessage() != null)
                Log.e(TAG, ex.getMessage());
            listener.onRetrieveVideosFailure(ex);
        }
    }

    @Override
    public void saveVideo(Uri file, String fileName,  onSaveVideoListener listener) {

        try {
            InputStream in = App.getContext().getContentResolver().openInputStream(file); // Uri
            ContextWrapper cw = new ContextWrapper(App.getContext());
            File directory = cw.getDir("VoiceMod", Context.MODE_PRIVATE);
            File newfile = new File(directory.getAbsolutePath() + "/" + fileName);
            OutputStream out = new FileOutputStream(newfile); // file
            byte[] buf = new byte[1024];
            int len;
            assert in != null;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception ex) {
            if (ex.getMessage() != null)
                Log.e(TAG, ex.getMessage());
            listener.onSaveVideoFailure(ex);
        }
    }

}
