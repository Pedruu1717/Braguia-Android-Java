package com.braguia;



import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Map {

    private static final String begin_map = "https://www.google.com/maps/dir/?api=1";
    private static final String add_origin = "&origin=";
    private static final String add_destination = "&destination=";
    private static final String add_waypoint = "&waypoints=";
    private static final String add_delimiter = "|";

    /***********************************************************************************************
     * FUNCTION:    openRoute
     * DESCRIPTION: Given an activity and a list of coords it will open up google maps in route mode
     **********************************************************************************************/
    public static void openRoute(AppCompatActivity activity, List<String> places){
        String uri = makeRoute(places);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try
        {
            activity.startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                activity.startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(activity, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

    /***********************************************************************************************
     * FUNCTION:    makeRoute
     * DESCRIPTION: Receives a list of coords in String format and makes a link to then use
     *              the google maps application
     **********************************************************************************************/
    private static String makeRoute(List<String> places){
        StringBuilder url = new StringBuilder(begin_map);

        if(places.size() > 2){
            url.append(add_waypoint);
            url.append(String.join(add_delimiter, places.subList(0, places.size() - 1)));
        }

        url.append(add_destination + places.get(places.size() - 1));

        return url.toString();
    }


}