package com.braguia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadsImage extends AsyncTask<Pair<Context, List<String>>, Void,Void> {

    @SafeVarargs
    @Override
    protected final Void doInBackground(Pair<Context, List<String>>... input) {
        Context context = input[0].first;
        List<URL> urls = new ArrayList<>();
        try {
            for(String string : input[0].second){
                if(string.endsWith(".jpg"))urls.add(new URL(string));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        List<Bitmap> bms = new ArrayList<>();
        try {
            for (URL url : urls) {
                bms.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/AndroidDvlpr"); //Creates app specific folder

        if(!path.exists()) {
            path.mkdirs();
        }

        File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".png"); // Imagename.png
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            for (Bitmap bm : bms) bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            assert out != null;
            out.flush();
            out.close();
            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(context, new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    // Log.i("ExternalStorage", "Scanned " + path + ":");
                    //    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } catch(Exception ignored) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}