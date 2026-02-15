package com.braguia.ui;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.braguia.DownloadsImage;
import com.braguia.Map;
import com.braguia.Permission;
import com.braguia.R;

import com.braguia.model.Pin;
import com.braguia.viewModel.PinViewModel;
import com.braguia.viewModel.UserViewModel;


import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PinInfoActivity extends AppCompatActivity {

    private PinViewModel pinViewModel;
    private Pin pin;
    private List<String> coordinates = new ArrayList<>();
    List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_info);

        Bundle extras = getIntent().getExtras();

        String pin_id = Integer.toString(extras.getInt("pin_id"));

        // Get a new or existing ViewModel from the ViewModelProvider.
        pinViewModel = new ViewModelProvider(this).get(PinViewModel.class);

        setPin(pin_id);

        ImageButton open_map_btn = findViewById(R.id.btn_open_map);
        open_map_btn.setOnClickListener( view -> {
            Map.openRoute(PinInfoActivity.this, coordinates);
        });

        ImageButton downloader_btn = findViewById(R.id.btn_download);
        downloader_btn.setOnClickListener( view -> {
            // fazer download de tudo
            if(urls == null) return;

            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ;

            if(!Permission.checkPermissions(PinInfoActivity.this, permissions)){
                Permission.requestPermissions(PinInfoActivity.this, permissions, 1003);
            } else {
                //Asynctask to create a thread to downlaod image in the background
                new DownloadsImage().execute(new Pair(PinInfoActivity.this, urls));
            }

        });

        

    }

    private void setMapCoordinates(String pin_lat, String pin_lng, String pin_alt) {
        String xy = pin_lat + ", " + pin_lng;
        coordinates.add(xy);
    }


    private void setCarousel(String url, String name) {
        ImageCarousel carousel = findViewById(R.id.carousel);
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(url,name));
        carousel.addData(list);
    }


    private void setPin(String pin_id) {
        // Get Pin.
        pinViewModel.getPinById(pin_id).observe(this, pinBD -> {
            if (pinBD == null) {
                Log.i("Null", "Expected Pin found null.");
                return;
            }

            Log.i("Pin found", "Pin loaded: " + pinBD.getPin_name());
            pin = pinBD;
            setPinInfo(pinBD);
            setMapCoordinates(pinBD.getPin_lat(), pinBD.getPin_lng(), pinBD.getPin_alt());
            if(pinBD.getUrls() != null && pinBD.getPin_name() != null) {
                String url = pinBD.getUrls().split(";")[0].replace("http","https");
                urls.add(url);
                setCarousel(url, pinBD.getPin_name());
            }

            // Only show map button if user is premium.
            UserViewModel mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            mUserViewModel.getAllUsers().observe(this, users -> {

                if (users.isEmpty() || !users.get(0).getUserType().equals("Premium")) return;

                ImageButton open_map_btn = findViewById(R.id.btn_open_map);
                open_map_btn.setClickable(true);
                open_map_btn.setVisibility(View.VISIBLE);

                ImageButton downloader_btn = findViewById(R.id.btn_download);
                downloader_btn.setClickable(true);
                downloader_btn.setVisibility(View.VISIBLE);

            });

        });
    }

    private void setPinInfo(Pin p) {
        String pin_name = p.getPin_name();
        String pin_desc = p.getPin_desc();
        String pin_lat = p.getPin_lat();
        String pin_lng = p.getPin_lng();
        String pin_alt = p.getPin_alt();

        TextView name_tv = findViewById(R.id.name);
        TextView desc_tv = findViewById(R.id.description);
        TextView latitude_tv = findViewById(R.id.latitude);
        TextView longitude_tv = findViewById(R.id.longitude);
        TextView altitude_tv = findViewById(R.id.altitude);

        name_tv.append(": " + pin_name);
        desc_tv.append(": " + pin_desc);
        latitude_tv.append(": " + pin_lat);
        longitude_tv.append(": " + pin_lng);
        altitude_tv.append(": " + pin_alt);
    }



}
