package com.testjob.app;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import com.testjob.app.adapters.ListAdapter;
import com.testjob.app.adapters.PictureSliderAdapter;
import com.viewpagerindicator.UnderlinePageIndicator;

public class MainFragment extends Fragment {
    private int mLastTop = 0;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final View pictureSlider = inflater.inflate(R.layout.picture_slider, container, false);
        ViewPager viewPictures = (ViewPager) pictureSlider.findViewById(R.id.up_sliding_picture);
        PictureSliderAdapter pictureSliderAdapter = new PictureSliderAdapter(getActivity());
        viewPictures.setAdapter(pictureSliderAdapter);
        UnderlinePageIndicator pictureIndicator =
                (UnderlinePageIndicator) pictureSlider.findViewById(R.id.sliding_picture_underline_indicator);
        pictureIndicator.setViewPager(viewPictures);

        ListAdapter listAdapter = new ListAdapter(getActivity());
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        listView.addHeaderView(pictureSlider);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                parallax(pictureSlider);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                parallax(pictureSlider);
            }
        });

        FetchArticleTask fetchArticleTask = new FetchArticleTask(getActivity(), listAdapter, pictureSliderAdapter);
        fetchArticleTask.execute();

        return rootView;
    }

    private void parallax(final View v) {
        final Rect r = new Rect();
        v.getLocalVisibleRect(r);

        if (mLastTop != r.top) {
            mLastTop = r.top;
            v.post(new Runnable() {
                @Override
                public void run() {
                    v.setTranslationY((float) r.top / 2.0F);
                }
            });
        }
    }

}
