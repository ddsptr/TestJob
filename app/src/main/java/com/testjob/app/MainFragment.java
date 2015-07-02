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
    private final static String LOG_TAG = MainFragment.class.getSimpleName();
    private int mLastTop = 0;
    private String mActionBarTitle;
    private boolean mArticleTitle = false;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final View pictureSlider = inflater.inflate(R.layout.picture_slider, null, false);
        ViewPager viewPictures = (ViewPager) pictureSlider.findViewById(R.id.up_sliding_picture);
        PictureSliderAdapter pictureSliderAdapter = new PictureSliderAdapter(getActivity());
        viewPictures.setAdapter(pictureSliderAdapter);
        UnderlinePageIndicator pictureIndicator =
                (UnderlinePageIndicator) pictureSlider.findViewById(R.id.sliding_picture_underline_indicator);
        pictureIndicator.setViewPager(viewPictures);

        final ListAdapter listAdapter = new ListAdapter(getActivity());
        FetchArticleTask fetchArticleTask = new FetchArticleTask(getActivity(), listAdapter, pictureSliderAdapter);
        fetchArticleTask.execute();

        mActionBarTitle = (String) getActivity().getTitle();

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.addHeaderView(pictureSlider);
        listView.setAdapter(listAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                parallax(pictureSlider);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                parallax(pictureSlider);
                handleVisiblePositions(view, listAdapter);
            }
        });

        return rootView;
    }

    private void handleVisiblePositions(AbsListView view, ListAdapter listAdapter) {
        int firstVisiblePosition = view.getFirstVisiblePosition();
        if ((firstVisiblePosition > 0) && (!mArticleTitle)) {
            mArticleTitle = true;
            ((MainActivity) getActivity()).setCustomTitle(listAdapter.getTitle());
        } else if ((firstVisiblePosition == 0) && (mArticleTitle)) {
            mArticleTitle = false;
            getActivity().setTitle(mActionBarTitle);
        }
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
