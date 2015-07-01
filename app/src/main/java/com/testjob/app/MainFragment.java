package com.testjob.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.testjob.app.adapters.ListAdapter;
import com.testjob.app.adapters.PictureSliderAdapter;
import com.viewpagerindicator.UnderlinePageIndicator;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ViewPager viewPictures = (ViewPager) rootView.findViewById(R.id.up_sliding_picture);
        PictureSliderAdapter pictureSliderAdapter = new PictureSliderAdapter(getActivity());
        viewPictures.setAdapter(pictureSliderAdapter);
        UnderlinePageIndicator pictureIndicator =
                (UnderlinePageIndicator) rootView.findViewById(R.id.sliding_picture_underline_indicator);
        pictureIndicator.setViewPager(viewPictures);

        ListAdapter listAdapter = new ListAdapter(getActivity());
        FetchArticleTask fetchArticleTask = new FetchArticleTask(getActivity(), listAdapter, pictureSliderAdapter);
        fetchArticleTask.execute();

        return rootView;
    }
}
