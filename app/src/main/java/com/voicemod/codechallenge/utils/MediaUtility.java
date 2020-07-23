package com.voicemod.codechallenge.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.voicemod.codechallenge.model.Video;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MediaUtility {



    private Uri loadVideoFromInternalStorage(String filePath){

        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+filePath);
        return uri;

    }

    public static void SaveMediaFile(Context context, Uri mediaFile, String fileName) {
        try {
            InputStream in = context.getContentResolver().openInputStream(mediaFile); // Uri
            ContextWrapper cw = new ContextWrapper(context);
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
        } catch (Exception e) {
        }
    }

    public static List<File> fetchVideosFromGallery(Context context){
        ArrayList<File> fileList = new ArrayList<File>();

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("VoiceMod", Context.MODE_PRIVATE);
        File[] listFile = directory.listFiles();

        if (listFile != null && listFile.length > 0) {

            for (File aListFile : listFile) {

               /* if (aListFile.isDirectory()) {
                    fileList.add(aListFile);

                } else {*/
                    if (aListFile.isFile()) ;
                    {
                        fileList.add(aListFile);
                    }
               // }
            }
        }
        return fileList;
    }

}
