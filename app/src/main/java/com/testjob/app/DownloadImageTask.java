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
    ImageCache mImageCache;
    String mUrl;
    Bitmap mNotAvailable;

    public DownloadImageTask(ImageView imageView, ImageCache imageCache, Bitmap notAvailable) {
        mImageView = imageView;
        mImageCache = imageCache;
        mNotAvailable = notAvailable;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        mUrl = urls[0];
        Bitmap picture = null;
        try {
            InputStream in = new URL(mUrl).openStream();
            picture = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return mNotAvailable;
        }
        return picture;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mImageCache.put(mUrl, result);
        mImageView.setImageBitmap(result);
    }
}
