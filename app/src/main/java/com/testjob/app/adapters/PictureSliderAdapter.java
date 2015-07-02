package com.testjob.app.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.testjob.app.DownloadImageTask;
import com.testjob.app.R;

import java.util.ArrayList;

/**
 * Created by dds on 30.06.15.
 */
public class PictureSliderAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mPictures = new ArrayList<String>();

    public PictureSliderAdapter(Context context) {
        mContext = context;
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
        new DownloadImageTask(imageView).execute(mPictures.get(position));
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
