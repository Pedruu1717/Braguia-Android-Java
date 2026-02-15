package com.braguia.ui;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.braguia.DownloadsImage;
import com.braguia.Map;
import com.braguia.Permission;
import com.braguia.R;
import com.braguia.model.Edge;
import com.braguia.model.Pin;
import com.braguia.model.Trail;
import com.braguia.viewModel.PinViewModel;
import com.braguia.viewModel.TrailsViewModel;
import com.braguia.viewModel.UserViewModel;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;

import java.util.ArrayList;
import java.util.List;

public class TrailInfoActivity extends AppCompatActivity {

    private TrailsViewModel trailsViewModel;
    private PinViewModel pinViewModel;
    private Trail trail;
    private List<Edge> trail_edges;
    private List<Pin> trail_pins = new ArrayList<>();

    private List<String> places =new ArrayList<>();

    List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_info);

        Bundle extras = getIntent().getExtras();

        String trail_id = Integer.toString(extras.getInt("trail_id"));

        // Get a new or existing ViewModel from the ViewModelProvider.
        trailsViewModel = new ViewModelProvider(this).get(TrailsViewModel.class);
        pinViewModel = new ViewModelProvider(this).get(PinViewModel.class);

        setTrail(trail_id);
        setEdges(trail_id);

        ImageButton open_map_btn = findViewById(R.id.btn_open_map);
        open_map_btn.setOnClickListener( view -> {
            Map.openRoute(TrailInfoActivity.this, places);
        });

        ImageButton downloader_btn = findViewById(R.id.btn_download);
        downloader_btn.setOnClickListener( view -> {
            // fazer download de tudo
            if(urls == null) return;

            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ;

            if(!Permission.checkPermissions(TrailInfoActivity.this, permissions)){
                Permission.requestPermissions(TrailInfoActivity.this, permissions, 1003);
            } else {
                //Asynctask to create a thread to downlaod image in the background
                new DownloadsImage().execute(new Pair(TrailInfoActivity.this, urls));
            }

        });

        GeofencingClient geofencingClient = LocationServices.getGeofencingClient(this);
    }

    private void setTrail(String trail_id) {
        // Get Trail.
        trailsViewModel.getTrailById(trail_id).observe(this, trailBD -> {
            if (trailBD == null) {
                Log.i("Null", "Expected Trail found null.");
            } else {
                Log.i("Trail found", "Trail loaded: " + trailBD.getTrailName());
                trail = trailBD;
                setTrailInfo(trailBD);
            }
        });
    }

    private void setEdges(String trail_id) {
        // Get and set the trail Edges.
        trailsViewModel.getEdgesByTrailId(trail_id).observe(this, trail_edgesBD -> {
            if (trail_edgesBD == null) {
                Log.i("Null", "Expected Edge found null.");
            } else {
                Log.i("Edges found", "Edges loaded!");
                trail_edges = trail_edgesBD;
                setPins(trail_edgesBD);
            }
        });
    }

    private void setMapRoute(String pin_lat, String pin_lng, String pin_alt) {
        String coordinates = pin_lat + ", " + pin_lng;
        places.add(coordinates);
    }

    private void setPins(List<Edge> trail_edges) {
        // Get and set the trail Pins.
        String id;
        for (int i = 0; i < trail_edges.size()+1; i++) {
            if(i <= trail_edges.size()-1) {
                id = trail_edges.get(i).getEdge_start();
            }            
            else{
                id = trail_edges.get(i-1).getEdge_end();
            }
            int finalI = i;
            pinViewModel.getPinById(id).observe(this, pinBD -> {
                if (pinBD == null) {
                    Log.i("Null", "Expected Pin found null.");
                } else {
                    Log.i("Pin found", "Pin loaded!");
                    trail_pins.add(pinBD);
                    setPinsInfo(pinBD);
                    setMapRoute(pinBD.getPin_lat(), pinBD.getPin_lng(), pinBD.getPin_alt());
                    if(pinBD.getUrls() != null && pinBD.getPin_name() != null) {
                        String url = pinBD.getUrls().split(";")[0].replace("http","https");
                        urls.add(url);
                        setCarousel(url, pinBD.getPin_name());
                    }

                    if(finalI == trail_edges.size()-1) {
                        // Only show map button if user is premium.
                        UserViewModel mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
                        mUserViewModel.getAllUsers().observe(this, users -> {
                            if (!users.isEmpty()) {
                                if(users.get(0).getUserType().equals("Premium")) {
                                    ImageButton open_map_btn = findViewById(R.id.btn_open_map);
                                    open_map_btn.setClickable(true);
                                    open_map_btn.setVisibility(View.VISIBLE);

                                    ImageButton downloader_btn = findViewById(R.id.btn_download);
                                    downloader_btn.setClickable(true);
                                    downloader_btn.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void setCarousel(String url, String name) {
        ImageCarousel carousel = findViewById(R.id.carousel);
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(url,name));
        carousel.addData(list);
    }

    private void setTrailInfo(Trail t) {
        String trail_img_url = t.getUrl();
        String trail_name = t.getTrailName();
        String trail_desc = t.getTrailDesc();
        Integer trail_duration = t.getTrailDuration();
        String trail_difficulty = t.getTrailDifficulty();

        TextView name_tv = findViewById(R.id.name);
        TextView duration_tv = findViewById(R.id.duration);
        TextView desc_tv = findViewById(R.id.description);
        TextView difficulty_tv = findViewById(R.id.difficulty);

        name_tv.append(": " + trail_name);
        duration_tv.append(": " + Integer.toString(trail_duration));
        desc_tv.append(": " + trail_desc);
        difficulty_tv.append(": " + trail_difficulty);
    }

    private void setPinsInfo(Pin pin) {
        StringBuilder text = new StringBuilder();
        String name = pin.getPin_name();
        text.append(name);
        text.append(", ");

        TextView pins_names_tv = findViewById(R.id.pins_names);
        pins_names_tv.append(text.toString());

    }


    //    private void setEdgesInfo(List<Edge> trail_edges) {
//        StringBuilder text = new StringBuilder();
//        for(int i=0; i<trail_edges.size(); i++) {
//            String name = trail_edges.get(i).getEdame();
//            text.append(name);
//            text.append(", ");
//        }
//
//        TextView pins_names_tv = findViewById(R.id.pins_names);
//        pins_names_tv.append(text.toString());
//    }
}
