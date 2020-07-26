package com.voicemod.codechallenge.videos;

import android.content.Intent;
import android.net.Uri;

import com.voicemod.codechallenge.App;
import com.voicemod.codechallenge.R;
import com.voicemod.codechallenge.model.Video;

import java.io.File;
import java.util.List;

public  class MainPresenter implements MainContract.Presenter,  MainContract.Model.onSaveVideoListener, MainContract.Model.onRetrieveVideosListener, MainContract.Model.onCameraListener {

    private MainContract.View mainView;
    private MainContract.Model mainModel;

    public MainPresenter(MainContract.View view){
        mainView = view;
        mainModel = new MainModel();
    }

    @Override
    public void retrieveVideos() {
        if (mainView != null)
            mainView.showProgress();

        mainModel.getVideos(this);
    }

    @Override
    public void onRetrieveVideosSuccess(List<Video> fileList) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.refreshVideos(fileList);
        }
    }



    @Override
    public void onDestroy() {
        mainView = null;
    }


    @Override
    public void saveVideo(Uri uri, String fileName) {
        if (mainView != null)
            mainView.showProgress();

        mainModel.saveVideo(uri, fileName, this);
    }

    @Override
    public void prepareCamera(String filename) {
        mainModel.onPrepareCameraIntent(filename, this);
    }


    @Override
    public void onRetrieveVideosFailure(Exception ex) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.showError(App.getContext().getString(R.string.TR_OCURRIO_UN_ERROR_RECUPERANDO_LOS_VIDEOS));
        }
    }

    @Override
    public void onSaveVideoSuccess() {
        if (mainView != null) {
            mainView.hideProgress();
            mainModel.getVideos(this); // Volvemos a cargar los vídeos
        }
    }

    @Override
    public void onSaveVideoFailure(Exception ex) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.showError(App.getContext().getString(R.string.TR_OCURRIO_UN_ERROR_GUARDANDO_VIDEO));
        }
    }

    @Override
    public void onCameraIsPrepared(Intent intent) {
        if (mainView != null){
            mainView.launchCamera(intent);
        }
    }
}
