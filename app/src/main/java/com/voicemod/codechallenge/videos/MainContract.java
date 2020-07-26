package com.voicemod.codechallenge.videos;

import android.content.Intent;
import android.net.Uri;

import com.voicemod.codechallenge.model.Video;

import java.util.List;

public class MainContract {

    public interface View{
        void showProgress();
        void hideProgress();
        void showError(String error);
        void refreshVideos(List<Video> videos);
        void launchCamera(Intent intent);
    }

    public interface Model{
        interface  onRetrieveVideosListener{
            void onRetrieveVideosSuccess(List<Video> fileList);
            void onRetrieveVideosFailure(Exception ex);
        }

        interface  onSaveVideoListener{
            void onSaveVideoSuccess();
            void onSaveVideoFailure(Exception ex);
        }

        interface onCameraListener{
            void onCameraIsPrepared(Intent intent);
        }

        void getVideos(onRetrieveVideosListener listener);
        void saveVideo(Uri file, String fileName,onSaveVideoListener listener);
        void onPrepareCameraIntent(String filename, onCameraListener listener);
    }

    public interface Presenter{
        void onDestroy();
        void retrieveVideos();
        void saveVideo(Uri uri, String fileName);
        void prepareCamera(String filename);
    }
}
