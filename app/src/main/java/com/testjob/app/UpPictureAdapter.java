package com.testjob.app;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

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
        return false;
    }
}
