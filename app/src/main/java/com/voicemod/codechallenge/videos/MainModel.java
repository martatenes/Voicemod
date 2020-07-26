package com.voicemod.codechallenge.videos;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.voicemod.codechallenge.App;
import com.voicemod.codechallenge.constants.Constants;
import com.voicemod.codechallenge.model.Video;
import com.voicemod.codechallenge.utils.SharedPrefUtils;

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

        Handler handler = new Handler();  //Lanzamos un hilo en segundo plano para no sobrecargar la memoria

        Runnable r = new Runnable()
        {
            Exception exc;
            @Override
            public void run()
            {
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
                    exc = ex;
                }

                handler.post(new Runnable()  //If you want to update the UI, queue the code on the UI thread
                {
                    public void run()
                    {
                        if (exc == null)  listener.onSaveVideoSuccess();
                        else  listener.onSaveVideoFailure(exc);

                        //Code to update the UI
                    }
                });
            }
        };

        Thread t = new Thread(r);
        t.start();

    }




    @Override
    public void onPrepareCameraIntent(String filename, onCameraListener listener) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File path = new File((App.getContext()).getFilesDir(), "VoiceMod");
        File filePath = new File(path + "/" + filename);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.TITLE, filename); // Nombre del archivo
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath); // Ruta donde será guardado
        // Extras según preferencias de usuario
        if (SharedPrefUtils.getBooleanData(App.getContext(), SharedPrefUtils.PREF_DURATION)){
            int minutes = SharedPrefUtils.getIntData(App.getContext(), SharedPrefUtils.PREF_DURATION_VALUE);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,  minutes*60);
        }
        if (SharedPrefUtils.getBooleanData(App.getContext(), SharedPrefUtils.PREF_SIZE)){
            int size = SharedPrefUtils.getIntData(App.getContext(), SharedPrefUtils.PREF_SIZE_VALUE);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,  size*1024*1024);
        }
        if (!SharedPrefUtils.getBooleanData(App.getContext(), SharedPrefUtils.PREF_QUALITY)){ // Por defecto la EXTRA_VIDEO_QUALITY es 1 en el intent
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,  0);  // Calidad baja
        }
       listener.onCameraIsPrepared(intent);
    }


}
