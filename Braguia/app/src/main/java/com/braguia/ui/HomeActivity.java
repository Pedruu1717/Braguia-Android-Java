package com.braguia.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.braguia.NotificationService;
import com.braguia.Permission;
import com.braguia.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final int MAPS_ACTIVITY_REQUEST_CODE = 1;
    private final String phone = new String("1234");

    public static final int PERMISSIONS_REQUEST_CODE = 1000;
    public static final int BACK_REQUEST_CODE = 1001;

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void permissionDialog(int title, String[] permissions, int request_code){
        // Create the object of AlertDialog,  Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        // Set the message show for the Alert time
        builder.setMessage(R.string.back_message);

        // Set Alert Title
        builder.setTitle(title);

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton(R.string.back_ok, (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            //Request location updates:
            Permission.requestPermissions(HomeActivity.this, permissions, request_code);
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton(R.string.back_cancel, (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the
            // location-related task you need to do.

            if (requestCode == PERMISSIONS_REQUEST_CODE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    String[] back_permission = {Manifest.permission.ACCESS_BACKGROUND_LOCATION};
                    permissionDialog(R.string.back_title, back_permission, BACK_REQUEST_CODE);
                }

                if (!isMyServiceRunning(NotificationService.class)) {
                    Intent serviceIntent = new Intent(this, NotificationService.class);
                    startService(serviceIntent);
                    // java.lang.RuntimeException: Unable to start activity ComponentInfo{com.braguia/com.braguia.ui.HomeActivity}: java.lang.IllegalArgumentException: permission cannot be null or empty
                }
            }

        } else {

            // permission denied, boo! Disable the
            // functionality that depends on this permission.

        }
    }


    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
        };
        Permission.requestPermissions(HomeActivity.this, permissions, PERMISSIONS_REQUEST_CODE);

        if (Permission.checkPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                && !isMyServiceRunning(NotificationService.class)) {
            Intent serviceIntent = new Intent(this, NotificationService.class);
            startService(serviceIntent);
        }


        RelativeLayout roteiros_btn = findViewById(R.id.place_card);
        RelativeLayout pontos_interesse_btn = findViewById(R.id.place_card_ek1);
        ImageView perfil_btn = findViewById(R.id.imageView);
        ImageView phone_btn = findViewById(R.id.btn_emmergency);

        perfil_btn.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MeuPerfilActivity.class);
            startActivity(intent);
        });

        pontos_interesse_btn.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.putExtra("context", "pins");
            startActivity(intent);
        });

        roteiros_btn.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.putExtra("context", "trails");
            startActivity(intent);
        });

        // Attach set on click listener to the button for initiating intent
        phone_btn.setOnClickListener(arg -> {
            // Getting instance of Intent with action as ACTION_CALL
            Intent phone_intent = new Intent(Intent.ACTION_DIAL);

            // Set data of Intent through Uri by parsing phone number
            phone_intent.setData(Uri.parse("tel:" + phone));

            // start Intent
            startActivity(phone_intent);
        });

    }

    @Override
    public void onBackPressed() {

    }

    private ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {

                }
            }
    );

}

