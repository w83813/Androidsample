package com.example.miis200;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ChoiceimageItemrecycler implements Serializable {

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String name;

    private Bitmap eyeimage;

    public String getName() {
        return name;
    }

    public Bitmap getEyeimage() {
        return eyeimage;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setEyeimage(Bitmap meyeimage) {
        this.eyeimage = meyeimage;
    }
}
