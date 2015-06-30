package com.testjob.app;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by dds on 30.06.15.
 */
public class UpPictureAdapter extends PagerAdapter {
    private Context mContext;
    private int [] mPictures = new int [] {
            R.drawable.kudago1,
            R.drawable.kudago2
    };

    UpPictureAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mPictures.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(mPictures[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
