package com.example.miis200;


import android.graphics.Bitmap;

public class ViewimageItemrecycler {
    private String Imagepath;
    private String Memo;
    private Bitmap Image;


    public ViewimageItemrecycler(String imagepath,String memo,Bitmap image){
        Imagepath = imagepath;
        Memo = memo;
        Image = image;

    }

    public String getImagepath() {
        return Imagepath;
    }

    public String getMemo() {
        return Memo;
    }

    public Bitmap getImage() {
        return Image;
    }


}
