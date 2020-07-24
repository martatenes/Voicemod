package com.voicemod.codechallenge.model;

import java.io.File;
import java.io.Serializable;

public class Video implements Serializable {

    File file;
    boolean isSelected = false;

    public Video(File file) {
        this.file = file;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
