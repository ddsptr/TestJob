package com.testjob.app.adapters;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.testjob.app.DownloadImageTask;
import com.testjob.app.ImageCache;
import com.testjob.app.R;
import com.testjob.app.Utils;

import java.util.ArrayList;

/**
 * Created by dds on 30.06.15.
 */
public class PictureSliderAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mPictures = new ArrayList<String>();
    private ImageCache mImageCache;
    private Bitmap mImageNotAvailable;

    public PictureSliderAdapter(Context context) {
        mContext = context;
        int memClass = ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 8;
        mImageCache = new ImageCache(cacheSize);
        mImageNotAvailable =
                Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.image_not_available, 240, 270);
    }

    public void addPicture(String picture) {
        mPictures.add(picture);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPictures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        inflateImage(imageView, mPictures.get(position));
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    private void inflateImage(ImageView imageView, String url) {
        Bitmap image = mImageCache.get(url);
        if (image != null) {
            imageView.setImageBitmap(image);
        } else {
            new DownloadImageTask(imageView, mImageCache, mImageNotAvailable).execute(url);
        }
    }
}
