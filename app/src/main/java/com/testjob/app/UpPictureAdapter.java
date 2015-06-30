package com.testjob.app;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by dds on 30.06.15.
 */
public class UpPictureAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
