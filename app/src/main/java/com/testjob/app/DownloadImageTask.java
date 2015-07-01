package com.testjob.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by dds on 01.07.15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView mImageView;

    public DownloadImageTask(ImageView imageView) {
        mImageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        String bitmapUrl = urls[0];
        Bitmap picture = null;
        try {
            InputStream in = new URL(bitmapUrl).openStream();
            picture = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return picture;
    }

    protected void onPostExecute(Bitmap result) {
        mImageView.setImageBitmap(result);
    }
}
