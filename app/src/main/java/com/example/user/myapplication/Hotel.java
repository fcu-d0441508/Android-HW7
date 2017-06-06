package com.example.user.myapplication;

import android.graphics.Bitmap;

/**
 * Created by user on 2017/6/6.
 */

public class Hotel {
    private Bitmap imgURL;
    private String address;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImgURL() {
        return imgURL;
    }

    public void setImgURL(Bitmap imgURL) {
        this.imgURL = imgURL;
    }


}
