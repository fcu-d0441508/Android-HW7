package com.example.user.myapplication;

import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/6.
 */

public class FirebaseThread extends Thread{

    private DataSnapshot dataSnapshot;

    public FirebaseThread(DataSnapshot dataSnapshot,HArrayAdapt adapter){
        this.dataSnapshot = dataSnapshot;
        this.adapter = adapter;
    }

    private HArrayAdapt adapter = null;
    private static final int LIST_HOTEL = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST_HOTEL: {
                    List<Hotel> hotels = (List<Hotel>)msg.obj;
                    refreshHotelList(hotels);
                    break;
                }
            }
        }
    };
    private void refreshHotelList(List<Hotel> hotels) {
        adapter.clear();
        adapter.addAll(hotels);
    }


    @Override
    public void run() {
        List<Hotel> lsHotel = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            DataSnapshot dsHName=ds.child("Name");
            DataSnapshot dsHAdd=ds.child("Add");
            String Name = (String)dsHName.getValue();
            String Add = (String)dsHAdd.getValue();
            DataSnapshot dsImg = ds.child("Picture2");
            String imgUrl = (String) dsImg.getValue();
            Bitmap hotelImg = getImgBitmap(imgUrl);

            Hotel aHotel = new Hotel();
            aHotel.setName(Name);
            aHotel.setAddress(Add);
            aHotel.setImgURL(hotelImg);
            lsHotel.add(aHotel);
            Log.v("hotel",Add +";" + Name);

            Message msg = new Message();
            msg.what = LIST_HOTEL;
            msg.obj = lsHotel;
            handler.sendMessage(msg);
        }
    }

    private Bitmap getImgBitmap(String imgUrl) {

            try {
                URL url = new URL(imgUrl);
                Bitmap bm = BitmapFactory.decodeStream(
                        url.openConnection()
                                .getInputStream());
                return bm;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


}
