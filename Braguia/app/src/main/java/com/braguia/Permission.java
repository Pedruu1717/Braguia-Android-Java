package com.braguia;

import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import java.util.ArrayList;
import java.util.List;
public class Permission {
    public static boolean checkPermission(AppCompatActivity activity, String permission){
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissions(AppCompatActivity activity, String[] permissions){
        Boolean result = true;
        for(String permission : permissions){
            result &= checkPermission(activity, permission);
        }
        return result;
    }


    public static void requestPermissions(AppCompatActivity activity, String[] permissions, int  id) {

        List<String> ask_permissions = new ArrayList<String>();
        for(String permission: permissions){
            if(!checkPermission(activity, permission)){
                ask_permissions.add(permission);
            }
        }

        if(ask_permissions.size() == 0) return;

        String[] final_permissions = new String[ ask_permissions.size()];
        ask_permissions.toArray( final_permissions );

        ActivityCompat.requestPermissions(activity, final_permissions, id); //, PERMISSION_ID
    }


    }








